package examples.inner_class;

import java.util.ArrayList;

import de.heinerkuecker.type_hierarchy_multiple_interfaces_renderer.TypeHierarchyMultipleInterfacesRenderer;

/**
 * Example for using {@link TypeHierarchyMultipleInterfacesRenderer}.
 *
 * @author Heiner K&uuml;cker
 */
public class InnerClassExample
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
            AbstractInner.class ,
            FinalInner.class
        };

        // Try different options
        hierarchyRenderer.withAbstractOrFinal = true;
        hierarchyRenderer.withEnum = true;
        hierarchyRenderer.withGenerics = true;
        hierarchyRenderer.withSuperClassAndSuperInterfaces = true;
        //hierarchyRenderer.excludes.add( Object.class );
        //hierarchyRenderer.javadocMode = true;
        //hierarchyRenderer.renderJavadocTooltips = true;

        System.out.println( hierarchyRenderer.render() );
    }

    @SuppressWarnings("serial")
    abstract class AbstractInner<T>
    extends ArrayList<T>
    {
        // no implementation
    }

    @SuppressWarnings("serial")
    final class FinalInner<T>
    extends ArrayList<T>
    {
        // no implementation
    }

}
