package examples.anonymous_local_class;

import java.util.ArrayList;

import de.heinerkuecker.type_hierarchy_multiple_interfaces.TypeHierarchyMultipleInterfacesRenderer;

/**
 * Example for using {@link TypeHierarchyMultipleInterfacesRenderer}.
 *
 * @author Heiner K&uuml;cker
 */
public class AnonymousClassExample
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
        final ArrayList<String> anonymous =
                new ArrayList<String>()
        {
            // no implementation
        };

        hierarchyRenderer.classes = new Class<?>[] {
            anonymous.getClass()
        };

        // Try different options
        hierarchyRenderer.withAbstractOrFinal = true;
        hierarchyRenderer.withEnum = true;
        hierarchyRenderer.withAnonymOrLocal = true;
        hierarchyRenderer.withGenerics = true;
        //hierarchyRenderer.excludes.add( Object.class );

        System.out.println( hierarchyRenderer.render() );
    }

}
