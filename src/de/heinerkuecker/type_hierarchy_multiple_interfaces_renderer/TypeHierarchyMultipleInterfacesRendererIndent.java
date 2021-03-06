package de.heinerkuecker.type_hierarchy_multiple_interfaces_renderer;

import static de.heinerkuecker.type_hierarchy_multiple_interfaces_renderer.TypeHierarchyMultipleInterfacesRenderer.classToStr;

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
    private final boolean withAbstractOrFinal;

    private final boolean withEnum;

    private final boolean withAnonymOrLocal;

    private final boolean withGenerics;

    //final boolean withSuperClassAndSuperInterfaces;

    //final TypeHierarchyMultipleInterfacesRendererIndent parent;

    // reference to global data structure
    private final Map<Class<?>, Set<Class<?>>> extenderAndImplementerMap;

    private final boolean renderJavadocTooltips;

    String lineSeparatorStr = "";

    private /*final*/ Class<?>[] classes;

    /**
     * Constructor.
     */
    TypeHierarchyMultipleInterfacesRendererIndent(
            final boolean withAbstractOrFinal ,
            final boolean withEnum ,
            final boolean withAnonymOrLocal ,
            final boolean withGenerics ,
            //final boolean withSuperClassAndSuperInterfaces ,
            final boolean renderJavadocTooltips ,
            //final TypeHierarchyMultipleInterfacesRendererIndent parent ,
            final Map<Class<?>, Set<Class<?>>> extenderAndImplementerMap ,
            //final String lineSeparatorStr ,
            final Class<?>[] classes )
    {
        this.withAbstractOrFinal = withAbstractOrFinal;
        this.withEnum = withEnum;
        this.withAnonymOrLocal = withAnonymOrLocal;
        this.withGenerics = withGenerics;
        //this.withSuperClassAndSuperInterfaces = withSuperClassAndSuperInterfaces;

        this.renderJavadocTooltips = renderJavadocTooltips;
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
            if ( this.renderJavadocTooltips &&
                    clazz != null &&
                    superClassAndInterfaces.contains( clazz ) )
            {
                if ( tooltipBuff.length() != 0 )
                {
                    //tooltipBuff.append( ", " );
                    // https://stackoverflow.com/questions/18606877/how-can-i-add-new-line-linebreak-character-in-title-attribute-in-html?noredirect=1&lq=1
                    tooltipBuff.append( "&#010;" );
                }

                final String classStr =
                        //.getName()
                        classToStr(
                                clazz ,
                                this.withAbstractOrFinal ,
                                this.withEnum ,
                                this.withAnonymOrLocal ,
                                this.withGenerics ,
                                //withSuperClassAndSuperInterfaces
                                false );

                tooltipBuff.append( classStr );
            }

            if ( clazz == null )
            {
                // removed class
                buff.append( removedClassCrossingStr );
            }
            else if ( superClassAndInterfaces.contains( clazz ) )
            {
                // line connector
                if ( this.renderJavadocTooltips )
                {
                    final String classStr =
                            //.getName()
                            classToStr(
                                    clazz ,
                                    this.withAbstractOrFinal ,
                                    this.withEnum ,
                                    this.withAnonymOrLocal ,
                                    this.withGenerics  ,
                                    //withSuperClassAndSuperInterfaces
                                    false );

                    buff.append( "<b title='" + classStr + "'>+</b>" );
                }
                else
                {
                    buff.append( '+' );
                }

                if ( this.renderJavadocTooltips )
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
                if ( this.renderJavadocTooltips )
                {
                    //buff.append( "<b title='" + clazz/*.getName()*/ + "'>|</b>" );
                    final String classStr =
                            //.getName()
                            classToStr(
                                    clazz ,
                                    this.withAbstractOrFinal ,
                                    this.withEnum ,
                                    this.withAnonymOrLocal ,
                                    this.withGenerics  ,
                                    //withSuperClassAndSuperInterfaces
                                    false );

                    buff.append( "<b title='" + classStr + "'>|</b>" );
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

        if ( this.renderJavadocTooltips )
        {
            final String classStr =
                    //.getName()
                    classToStr(
                            classToAdd ,
                            this.withAbstractOrFinal ,
                            this.withEnum ,
                            this.withAnonymOrLocal ,
                            this.withGenerics  ,
                            //withSuperClassAndSuperInterfaces
                            false );

            this.lineSeparatorStr =
                    this.lineSeparatorStr +
                    "<b title='" + classStr + "'>|</b>  ";
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
     * Vermeiden von vertikalen Linien, die ins Leere f�hren.
     *
     * Avoid vertical lines that lead nowhere.
     *
     * @param subClassToRemove already rendered class (or interface, enum and so on)
     */
    public void removeSubClassWithoutTrim(
            final Class<?> subClassToRemove )
    {
        //if ( subClassToRemove.equals( java.util.AbstractQueue.class ) )
        //{
        //    System.out.println( "debug break: " + subClassToRemove ); // TODO on�y for debug
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

                this.removeClassWithoutTrim( clazz );
            }
        }
    }

    private void removeClassWithoutTrim(
            final Class<?> classToRemove )
    {
        for ( int i = 0 ; i < this.classes.length ; ++i )
        {
            final Class<?> clazz = this.classes[ i ];

            if ( classToRemove.equals( clazz ) )
            {
                this.classes[ i ] = null;

                updateLineSeparatorStr();

                break;
            }
        }
    }

    void trim()
    {
        int classesLastIndexWoRightNulls = this.classes.length - 1;
        for ( ; classesLastIndexWoRightNulls >= 0 ; --classesLastIndexWoRightNulls )
        {
            if ( this.classes[ classesLastIndexWoRightNulls ] != null )
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

        updateLineSeparatorStr();
    }

    private void updateLineSeparatorStr()
    {
        final StringBuilder buff = new StringBuilder();

        for ( int i = 0 ; i < this.classes.length ; ++i )
        {
            if ( this.classes[ i ] == null )
            {
                buff.append( ' ' );
            }
            else
            {
                if ( this.renderJavadocTooltips )
                {
                    final String classStr =
                            //.getName()
                            classToStr(
                                    this.classes[ i ] ,
                                    this.withAbstractOrFinal ,
                                    this.withEnum ,
                                    this.withAnonymOrLocal ,
                                    this.withGenerics  ,
                                    //withSuperClassAndSuperInterfaces
                                    false );

                    buff.append( "<b title='" + classStr + "'>|</b>" );
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
                "classes=" + Arrays.toString(this.classes) + "\n" +
                "lineSeparatorStr=" + this.lineSeparatorStr /*+ ", "*/
                //"]"
                ;
    }


}
