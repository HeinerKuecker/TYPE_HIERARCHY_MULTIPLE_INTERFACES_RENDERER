package de.heinerkuecker.type_hierarchy_multiple_interfaces.examples.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

import de.heinerkuecker.type_hierarchy_multiple_interfaces.TypeHierarchyMultipleInterfacesRenderer;

/**
 * Example for using {@link TypeHierarchyMultipleInterfacesRenderer}.
 *
 * @author Heiner K&uuml;cker
 */
public class NumbersExample
{
    /**
     * Example for usage.
     *
     * @param args unused
     */
    public static void main(
            final String[] args )
    {
        final TypeHierarchyMultipleInterfacesRenderer hierarchyRenderer =
                new TypeHierarchyMultipleInterfacesRenderer();

        hierarchyRenderer.classes = new Class<?>[] {
            // TODO comment in to test Exception for not connected class: Iterable.class ,
            java.nio.file.AccessMode.class ,
            Byte.class ,
            Short.class ,
            Character.class ,
            Integer.class ,
            Long.class ,
            Float.class ,
            Double.class ,
            BigInteger.class ,
            BigDecimal.class ,
            AtomicInteger.class ,
            AtomicLong.class ,
            LongAccumulator.class ,
            LongAdder.class ,
            DoubleAccumulator.class ,
            DoubleAdder.class
        };

        // Try different options
        //hierarchyRenderer.excludes.add( Object.class );
        //hierarchyRenderer.javadocMode = true;
        //hierarchyRenderer.renderJavadocTitleAttribute = true;

        System.out.println( hierarchyRenderer.render() );
    }

}
