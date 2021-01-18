package de.heinerkuecker.type_hierarchy_multiple_interfaces;

import static de.heinerkuecker.type_hierarchy_multiple_interfaces.TypeHierarchyMultipleInterfacesRenderer.classToStr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 *
 * @author Heiner K&uuml;cker
 */
class TypeHierarchyMultipleInterfacesRendererIndent
{
    //final TypeHierarchyMultipleInterfacesRendererIndent parent;

    // reference to global data structure
    private final Map<Class<?>, Set<Class<?>>> extenderAndImplementerMap;

    final boolean renderJavadocTitleAttribute;

    String lineSeparatorStr = "";

    private /*final*/ Class<?>[] classes;

    /**
     * Constructor.
     *
     * @param lineSeparatorStr
     * @param classes
     */
    TypeHierarchyMultipleInterfacesRendererIndent(
            final boolean renderJavadocTitleAttribute ,
            //final TypeHierarchyMultipleInterfacesRendererIndent parent ,
            final Map<Class<?>, Set<Class<?>>> extenderAndImplementerMap ,
            //final String lineSeparatorStr ,
            final Class<?>[] classes )
    {
        this.renderJavadocTitleAttribute = renderJavadocTitleAttribute;
        //this.parent = parent;
        this.extenderAndImplementerMap = extenderAndImplementerMap;

        //this.lineSeparatorStr = lineSeparatorStr;
        this.classes = classes;
    }

    public String getConnectedStr(
            final Set<Class<?>> superClassAndInterfaces )
    {
        final StringBuilder buff = new StringBuilder();

        // most left indent
        String subIndentStr = "  ";
        String removedClassCrossingStr = " ";

        final StringBuilder tooltipBuff = new StringBuilder();

        for ( final Class<?> clazz : this.classes )
        {
            if ( renderJavadocTitleAttribute &&
                    clazz != null &&
                    superClassAndInterfaces.contains( clazz ) )
            {
                if ( tooltipBuff.length() != 0 )
                {
                    //tooltipBuff.append( ", " );
                    // https://stackoverflow.com/questions/18606877/how-can-i-add-new-line-linebreak-character-in-title-attribute-in-html?noredirect=1&lq=1
                    tooltipBuff.append( "&#010;" );
                }

                tooltipBuff.append( classToStr( clazz ) );
            }

            if ( clazz == null )
            {
                // removed class
                buff.append( removedClassCrossingStr );
            }
            else if ( superClassAndInterfaces.contains( clazz ) )
            {
                // line connector
                if ( renderJavadocTitleAttribute )
                {
                    buff.append( "<b title='" + classToStr( clazz )/*.getName()*/ + "'>+</b>" );
                }
                else
                {
                    buff.append( '+' );
                }

                if ( renderJavadocTitleAttribute )
                {
                    subIndentStr = "<b title='" + tooltipBuff + "'>--</b>";
                }
                else
                {
                    subIndentStr = "--";
                }
                removedClassCrossingStr = "-";
            }
            else
            {
                // crossing lines without connection
                if ( renderJavadocTitleAttribute )
                {
                    //buff.append( "<b title='" + clazz/*.getName()*/ + "'>|</b>" );
                    buff.append( "<b title='" + classToStr( clazz )/*.getName()*/ + "'>|</b>" );
                }
                else
                {
                    buff.append( "|" );
                }
            }

            buff.append( subIndentStr );
        }

        return buff.toString();
    }

    //public TypeHierarchyMultipleInterfacesRendererIndent add(
    //        final Class<?> classToAdd )
    //{
    //    // TODO only for develop
    //    for ( final Class<?> clazz : this.classes )
    //    {
    //        if ( classToAdd.equals( clazz ) )
    //        {
    //            throw new IllegalArgumentException(
    //                    "class already part of indent " + classToAdd );
    //        }
    //    }
    //
    //    final String newLineSeparatorStr =
    //            this.lineSeparatorStr +
    //            "|  ";
    //
    //    final ArrayList<Class<?>> newClassesList =
    //            new ArrayList<>(
    //                    Arrays.asList( this.classes ) );
    //
    //    newClassesList.add( classToAdd );
    //
    //    final Class<?>[] newClasses =
    //            newClassesList.toArray(
    //                    new Class<?>[ newClassesList.size() ] );
    //
    //    return
    //            new TypeHierarchyMultipleInterfacesRendererIndent(
    //                    this ,
    //                    this.extenderAndImplementerMap ,
    //                    newLineSeparatorStr ,
    //                    newClasses );
    //}

    public void add(
            final Class<?> classToAdd )
    {
        // TODO only for develop
        for ( final Class<?> clazz : this.classes )
        {
            if ( classToAdd.equals( clazz ) )
            {
                throw new IllegalArgumentException(
                        "class already part of indent " + classToAdd );
            }
        }

        //final String newLineSeparatorStr =
        //        this.lineSeparatorStr +
        //        "|  ";

        if ( renderJavadocTitleAttribute )
        {
            this.lineSeparatorStr =
                    this.lineSeparatorStr +
                    "<b title='" + classToStr( classToAdd )/*.getName()*/ + "'>|</b>  ";
        }
        else
        {
            this.lineSeparatorStr =
                    this.lineSeparatorStr +
                    "|  ";
        }

        //final ArrayList<Class<?>> newClassesList =
        //        new ArrayList<>(
        //                Arrays.asList( this.classes ) );
        //
        //newClassesList.add( classToAdd );
        //
        //final Class<?>[] newClasses =
        //        newClassesList.toArray(
        //                new Class<?>[ newClassesList.size() ] );

        final Class<?>[] newClasses =
                new Class<?>[ this.classes.length + 1 ];

        System.arraycopy(
                //src
                this.classes ,
                //srcPos
                0 ,
                //dest
                newClasses ,
                //destPos
                0 ,
                //length
                this.classes.length );

        newClasses[ newClasses.length - 1 ] = classToAdd;

        this.classes = newClasses;

        //return
        //        new TypeHierarchyMultipleInterfacesRendererIndent(
        //                this ,
        //                this.extenderAndImplementerMap ,
        //                newLineSeparatorStr ,
        //                newClasses );
    }

    /**
     * Vermeiden von vertikalen Linien, die ins Leere führen.
     *
     * Avoid vertical lines that lead nowhere.
     *
     * @param subClassToRemove already rendered class (or interface, enum and so on)
     */
    public void removeSubClass(
            final Class<?> subClassToRemove )
    {
        //if ( subClassToRemove.equals( java.util.AbstractQueue.class ) )
        //{
        //    System.out.println( "debug break: " + subClassToRemove ); // TODO onöy for debug
        //}

        // TODO wrong
        //if ( this.extenderAndImplementerMap.containsKey( subClassToRemove ) )
        //{
        //    // Es sind noch Untertypen des zu entfernenden Typen vorhanden
        //    // There are still sub-types of the type to be removed
        //    return;
        //}

        // create clone of view to avoid ConcurrentModificationException
        final ArrayList<Entry<Class<?>, Set<Class<?>>>> entrysClone =
                new ArrayList<>(
                        this.extenderAndImplementerMap.entrySet() );

        for ( final Entry<Class<?>, Set<Class<?>>> extenderAndImplementerEntry : entrysClone )
        {
            final Set<Class<?>> extenderAndImplementerSet = extenderAndImplementerEntry.getValue();

            extenderAndImplementerSet.remove( subClassToRemove );

            if ( extenderAndImplementerSet.isEmpty() )
            {
                final Class<?> clazz = extenderAndImplementerEntry.getKey();

                //if ( clazz.equals( java.util.AbstractQueue.class ) )
                //{
                //    System.out.println( "debug break: " + clazz ); // TODO only for debug
                //}

                this.extenderAndImplementerMap.remove( clazz );

                this.removeClass( clazz );
            }
        }
    }

    private void removeClass(
            final Class<?> classToRemove )
    {
        for ( int i = 0 ; i < this.classes.length ; ++i )
        {
            final Class<?> clazz = this.classes[ i ];

            if ( classToRemove.equals( clazz ) )
            {
                this.classes[ i ] = null;
                break;
            }
        }

        int classesLastIndexWoRightNulls = classes.length - 1;
        for ( ; classesLastIndexWoRightNulls >= 0 ; --classesLastIndexWoRightNulls )
        {
            if ( classes[ classesLastIndexWoRightNulls ] != null )
            {
                break;
            }
        }

        final int classesLengthWoRightNulls = classesLastIndexWoRightNulls + 1;

        final Class<?>[] newClasses = new Class<?>[ classesLengthWoRightNulls ];

        System.arraycopy(
                //src
                this.classes ,
                //srcPos
                0 ,
                //dest
                newClasses ,
                //destPos
                0 ,
                //length
                classesLengthWoRightNulls );

        this.classes = newClasses;

        //if ( this.parent != null )
        //{
        //    this.parent.removeClass( classToRemove );
        //}

        final StringBuilder buff = new StringBuilder();

        for ( int i = 0 ; i < this.classes.length ; ++i )
        {
            if ( this.classes[ i ] == null )
            {
                buff.append( ' ' );
            }
            else
            {
                if ( renderJavadocTitleAttribute )
                {
                    buff.append( "<b title='" + classToStr( this.classes[ i ] )/*.getName()*/ + "'>|</b>" );
                }
                else
                {
                    buff.append( '|' );
                }
            }

            buff.append( "  " );
        }

        this.lineSeparatorStr = buff.toString();
    }

    @Override
    public String toString()
    {
        return
                //"[" +
                "classes=" + Arrays.toString(classes) + "\n" +
                "lineSeparatorStr=" + lineSeparatorStr /*+ ", "*/
                //"]"
                ;
    }


}
