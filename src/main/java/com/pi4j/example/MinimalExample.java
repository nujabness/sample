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
import com.pi4j.io.gpio.analog.AnalogInput;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiMode;
import com.pi4j.util.Console;

/**
 * <p>This example fully describes the base usage of Pi4J by providing extensive comments in each step.</p>
 *
 * @author Frank Delporte (<a href="https://www.webtechie.be">https://www.webtechie.be</a>)
 * @version $Id: $Id
 */
public class MinimalExample {

    private static final int PIN_BUTTON = 24; // PIN 18 = BCM 24
    private static final int PIN_LED = 22; // PIN 15 = BCM 22

    private static int pressCount = 0;

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

        var imput4Conf = DigitalInput.newConfigBuilder(pi4j)
                .id("I 4")
                .name("I4")
                .address(4)
                .provider("pigpio-digital-input");

        var imput4 = pi4j.create(imput4Conf);

        var imput18Conf = DigitalInput.newConfigBuilder(pi4j)
                .id("I 18")
                .name("I18")
                .address(18)
                .provider("pigpio-digital-input");
        var imput18 = pi4j.create(imput18Conf);

        var imput1Conf = DigitalInput.newConfigBuilder(pi4j)
                .id("I 1")
                .name("I1")
                .address(1)
                .provider("pigpio-digital-input");
        var imput1 = pi4j.create(imput1Conf);


        var imput7Conf = DigitalInput.newConfigBuilder(pi4j)
                .id("I 7")
                .name("I7")
                .address(7)
                .provider("pigpio-digital-input");

        var imput7 = pi4j.create(imput7Conf);

        var spi10Conf = Spi
                .newConfigBuilder(pi4j)
                .id("SPI 10")
                .name("SPI10")
                .address(17)
                .mode(SpiMode.MODE_0)
                .baud(Spi.DEFAULT_BAUD);

        var spi10 = pi4j.create(spi10Conf);

//        var spi12Conf = Spi
//                .newConfigBuilder(pi4j)
//                .id("SPI 12")
//                .name("SPI12")
//                .address(12)
//                .mode(SpiMode.MODE_0)
//                .baud(Spi.DEFAULT_BAUD);
//
//        var spi12 = pi4j.create(spi12Conf);



        // OPTIONAL: print the registry
        PrintInfo.printRegistry(console, pi4j);

        console.println("VALEUR PIN 4 : " + imput4.state().getValue());
        console.println("VALEUR PIN 7 : " + imput7.state().getValue());
        console.println("VALEUR PIN 18 : " + imput18.state().getValue());
        console.println("VALEUR PIN 1 : " + imput1.state().getValue());
        console.println("VALEUR SPI : " + spi10.read());

        // ------------------------------------------------------------
        // Terminate the Pi4J library
        // ------------------------------------------------------------
        // We we are all done and want to exit our application, we must
        // call the 'shutdown()' function on the Pi4J static helper class.
        // This will ensure that all I/O instances are properly shutdown,
        // released by the the system and shutdown in the appropriate
        // manner. Terminate will also ensure that any background
        // threads/processes are cleanly shutdown and any used memory
        // is returned to the system.

        // Shutdown Pi4J
        pi4j.shutdown();
    }
}
