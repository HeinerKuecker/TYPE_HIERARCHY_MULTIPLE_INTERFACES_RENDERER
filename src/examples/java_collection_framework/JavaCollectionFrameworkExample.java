package examples.java_collection_framework;

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

import de.heinerkuecker.type_hierarchy_multiple_interfaces.TypeHierarchyMultipleInterfacesRenderer;

/**
 * Example for using {@link TypeHierarchyMultipleInterfacesRenderer}.
 *
 * @author Heiner K&uuml;cker
 */
public class JavaCollectionFrameworkExample
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

        // Try different options
        hierarchyRenderer.withAbstractOrFinal = true;
        hierarchyRenderer.withEnum = true;
        //hierarchyRenderer.excludes.add( Object.class );
        hierarchyRenderer.javadocMode = true;
        //hierarchyRenderer.renderJavadocTooltips = true;

        System.out.println( hierarchyRenderer.render() );
    }

}
