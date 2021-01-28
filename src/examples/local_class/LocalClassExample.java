package examples.local_class;

import java.util.HashSet;

import de.heinerkuecker.type_hierarchy_multiple_interfaces_renderer.TypeHierarchyMultipleInterfacesRenderer;

/**
 * Example for using {@link TypeHierarchyMultipleInterfacesRenderer}.
 *
 * @author Heiner K&uuml;cker
 */
public class LocalClassExample
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

        @SuppressWarnings("serial")
        class LocalClass
        extends HashSet<String>
        {
            // no implementation
        }

        hierarchyRenderer.classes = new Class<?>[] {
            LocalClass.class
        };

        // Try different options
        hierarchyRenderer.withAbstractOrFinal = true;
        hierarchyRenderer.withEnum = true;
        hierarchyRenderer.withAnonymOrLocal = true;
        hierarchyRenderer.withSuperClassAndSuperInterfaces = true;
        //hierarchyRenderer.excludes.add( Object.class );

        System.out.println( hierarchyRenderer.render() );
    }

}
