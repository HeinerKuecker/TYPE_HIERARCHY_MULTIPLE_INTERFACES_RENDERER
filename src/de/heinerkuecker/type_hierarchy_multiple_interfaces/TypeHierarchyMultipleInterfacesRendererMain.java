package de.heinerkuecker.type_hierarchy_multiple_interfaces;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

import javax.sql.rowset.spi.XmlWriter;

/**
 * Examples for using {@link TypeHierarchyMultipleInterfacesRenderer}.
 *
 * @author Heiner K&uuml;cker
 */
public class TypeHierarchyMultipleInterfacesRendererMain
{
    /**
     * Example for using.
     *
     * @param args unused
     */
    public static void main(
            final String[] args )
    {
        final TypeHierarchyMultipleInterfacesRenderer hierarchyRenderer = new TypeHierarchyMultipleInterfacesRenderer();

        // ========================================================

        // TODO use reflections lib to find sub classes
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

        //hierarchyRenderer.excludes.add( Object.class );
        //hierarchyRenderer.javadocMode = true;
        //hierarchyRenderer.renderJavadocTitleAttribute = true;

        System.out.println( hierarchyRenderer.render() );

        // ========================================================

        hierarchyRenderer.classes = new Class<?>[] {
            ArrayList.class ,
            LinkedList.class ,
            ArrayDeque.class ,
            ArrayBlockingQueue.class ,
            BlockingDeque.class ,
            BlockingQueue.class ,
            DelayQueue.class ,
            SynchronousQueue.class ,
            PriorityBlockingQueue.class ,
            ConcurrentLinkedDeque.class ,
            ConcurrentLinkedQueue.class ,
            ConcurrentHashMap.class ,
            LinkedBlockingQueue.class ,
            LinkedBlockingDeque.class ,
            LinkedTransferQueue.class ,
            HashMap.class ,
            HashSet.class ,
            LinkedHashMap.class ,
            LinkedHashSet.class ,
            TreeMap.class ,
            TreeSet.class ,
            Stack.class ,
            CopyOnWriteArrayList.class ,
            CopyOnWriteArraySet.class
        };

        //hierarchyRenderer.excludes.add( Object.class );
        //hierarchyRenderer.javadocMode = true;
        //hierarchyRenderer.renderJavadocTitleAttribute = true;

        System.out.println( hierarchyRenderer.render() );

        // ========================================================

        hierarchyRenderer.classes = new Class<?>[] {
            String.class ,
            StringBuffer.class ,
            StringBuilder.class ,
            ByteArrayOutputStream.class ,
            System.out.getClass() ,
            FileWriter.class ,
            XmlWriter.class ,
            BufferedReader.class
        };

        //hierarchyRenderer.excludes.add( Object.class );
        //hierarchyRenderer.javadocMode = true;
        //hierarchyRenderer.renderJavadocTitleAttribute = true;

        System.out.println( hierarchyRenderer.render() );
    }

}
