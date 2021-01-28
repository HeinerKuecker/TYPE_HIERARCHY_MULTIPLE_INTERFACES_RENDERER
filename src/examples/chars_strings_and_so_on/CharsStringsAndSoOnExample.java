package examples.chars_strings_and_so_on;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;

import javax.sql.rowset.spi.XmlWriter;

import de.heinerkuecker.type_hierarchy_multiple_interfaces_renderer.TypeHierarchyMultipleInterfacesRenderer;

/**
 * Example for using {@link TypeHierarchyMultipleInterfacesRenderer}.
 *
 * @author Heiner K&uuml;cker
 */
public class CharsStringsAndSoOnExample
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
            String.class ,
            StringBuffer.class ,
            StringBuilder.class ,
            ByteArrayOutputStream.class ,
            System.out.getClass() ,
            FileWriter.class ,
            XmlWriter.class ,
            BufferedReader.class
        };

        // Try different options
        hierarchyRenderer.withAbstractOrFinal = true;
        hierarchyRenderer.withEnum = true;
        hierarchyRenderer.withGenerics = true;
        //hierarchyRenderer.excludes.add( Object.class );
        //hierarchyRenderer.javadocMode = true;
        //hierarchyRenderer.renderJavadocTooltips = true;

        System.out.println( hierarchyRenderer.render() );
    }

}
