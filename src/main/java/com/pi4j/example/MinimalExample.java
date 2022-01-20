package com.pi4j.example;

import com.pi4j.Pi4J;
import com.pi4j.util.Console;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * <p>AnalogInputExample class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class MinimalExample {

    /** Constant <code>ANALOG_INPUT_PIN=4</code> */
    public static final int ANALOG_INPUT_PIN = 4;

    /**
     * <p>Constructor for AnalogInputExample.</p>
     */
    public MinimalExample() {
    }

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Basic Analog Input Example");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        var pi4j = Pi4J.newAutoContext();

        // create an analog input instance using the default analog input provider
        var input = pi4j.analogInput().create(ANALOG_INPUT_PIN);

        // setup an analog input listener to listen for any value changes on the analog input
        input.addListener(console::println);

        // lets read the analog output state
        console.print("THE STARTING ANALOG INPUT [" + input + "] VALUE IS [");
        console.println(input.value() + "]");

        console.println("CHANGE INPUT VALUES VIA I/O HARDWARE AND CHANGE EVENTS WILL BE PRINTED BELOW:");

        // wait (block) for user to exit program using CTRL-C
        console.waitForExit();

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
        pi4j.shutdown();
    }
}
