package examples.generics_type;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.WildcardType;

import javax.lang.model.type.TypeVariable;

import de.heinerkuecker.type_hierarchy_multiple_interfaces_renderer.TypeHierarchyMultipleInterfacesRenderer;

/**
 * Example for using {@link TypeHierarchyMultipleInterfacesRenderer}.
 *
 * @author Heiner K&uuml;cker
 */
public class GenericsTypeExample
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
            Class.class ,
            GenericArrayType.class ,
            ParameterizedType.class ,
            TypeVariable.class ,
            WildcardType.class
        };

        // Try different options
        hierarchyRenderer.withAbstractOrFinal = true;
        hierarchyRenderer.withEnum = true;
        hierarchyRenderer.withGenerics = true;
        hierarchyRenderer.excludes.add( Object.class );
        hierarchyRenderer.withSuperClassAndSuperInterfaces = true;
        hierarchyRenderer.superClassAndSuperInterfacesMultiLined = true;
        //hierarchyRenderer.javadocMode = true;
        //hierarchyRenderer.renderJavadocTooltips = true;

        System.out.println( hierarchyRenderer.render() );
    }

}
