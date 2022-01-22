package com.pi4j.example;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  MinimalExample.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2021 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.pi4j.Pi4J;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiProvider;
import com.pi4j.util.Console;
import com.pi4j.util.StringUtil;

import java.nio.ByteBuffer;

/**
 * <p>This example fully describes the base usage of Pi4J by providing extensive comments in each step.</p>
 *
 * @author Frank Delporte (<a href="https://www.webtechie.be">https://www.webtechie.be</a>)
 * @version $Id: $Id
 */
public class MinimalExample {

    private static final int SPI_CHANNEL = 0;
    private static final int I2C_BUS = 1;
    private static final int I2C_DEVICE = 0x04;

    /**
     * This application blinks a led and counts the number the button is pressed. The blink speed increases with each
     * button press, and after 5 presses the application finishes.
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {
        // Create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // Print program title/header
        console.title("<-- The Pi4J Project -->", "Minimal Example project");

        // ************************************************************
        //
        // WELCOME TO Pi4J:
        //
        // Here we will use this getting started example to
        // demonstrate the basic fundamentals of the Pi4J library.
        //
        // This example is to introduce you to the boilerplate
        // logic and concepts required for all applications using
        // the Pi4J library.  This example will do use some basic I/O.
        // Check the pi4j-examples project to learn about all the I/O
        // functions of Pi4J.
        //
        // ************************************************************

        // ------------------------------------------------------------
        // Initialize the Pi4J Runtime Context
        // ------------------------------------------------------------
        // Before you can use Pi4J you must initialize a new runtime
        // context.
        //
        // The 'Pi4J' static class includes a few helper context
        // creators for the most common use cases.  The 'newAutoContext()'
        // method will automatically load all available Pi4J
        // extensions found in the application's classpath which
        // may include 'Platforms' and 'I/O Providers'
        var pi4j = Pi4J.newAutoContext();

        // ------------------------------------------------------------
        // Output Pi4J Context information
        // ------------------------------------------------------------
        // The created Pi4J Context initializes platforms, providers
        // and the I/O registry. To help you to better understand this
        // approach, we print out the info of these. This can be removed
        // from your own application.
        // OPTIONAL
        PrintInfo.printLoadedPlatforms(console, pi4j);
        PrintInfo.printDefaultPlatform(console, pi4j);
        PrintInfo.printProviders(console, pi4j);

        // OPTIONAL: print the registry
        PrintInfo.printRegistry(console, pi4j);


        var config = Spi.newConfigBuilder(pi4j)
                .id("my-spi-device")
                .name("My SPI Device")
                .address(SPI_CHANNEL)
                .baud(Spi.DEFAULT_BAUD)
                .build();

        // get a SPI I/O provider from the Pi4J context
        SpiProvider spiProvider = pi4j.provider("pigpio-spi");

        // use try-with-resources to auto-close SPI when complete
        try (var spi = spiProvider.create(config);) {


            // open SPI communications
            spi.open();

            // write data to the SPI channel

            // take a breath to allow time for the SPI
            // data to get updated in the SPI device
            Thread.sleep(100);

            // read data back from the SPI channel
            ByteBuffer buffer = spi.readByteBuffer(2);

            console.println("--------------------------------------");
            console.println("--------------------------------------");
            console.println("SPI [READ] :");
            console.println("  [BYTES]  0x" + StringUtil.toHexString(buffer.array()));
            console.println("  [STRING] " + new String(buffer.array()));
            console.println("--------------------------------------");
            console.println(spi.read());
            console.println("--------------------------------------");

        }

        // create I2C config
        var configI2C = I2C.newConfigBuilder(pi4j)
                .id("my-i2c-bus")
                .name("My I2C Bus")
                .bus(I2C_BUS)
                .device(I2C_DEVICE)
                .build();

        // get a serial I/O provider from the Pi4J context
        I2CProvider i2CProvider = pi4j.provider("pigpio-i2c");

        // use try-with-resources to auto-close I2C when complete
        try (var i2c = i2CProvider.create(configI2C);) {

            // we will be reading and writing to register address 0x01
            var register = i2c.register(2);


            // <-- read a single (8-bit) byte value from the I2C device register
            byte readByte = register.readByte();

            console.println("I2C READ BYTE: 0x" + Integer.toHexString(Byte.toUnsignedInt(readByte)));

            // <-- read a single (16-bit) word value from the I2C device register
            int readWord = register.readWord();

            console.println("I2C READ WORD: 0x" + Integer.toHexString(readWord));

            // <-- read ByteBuffer of specified length from the I2C device register
            ByteBuffer readBuffer = register.readByteBuffer(2);

            console.println("I2C READ BUFFER: 0x" + StringUtil.toHexString(readBuffer));
        }
    }
}
