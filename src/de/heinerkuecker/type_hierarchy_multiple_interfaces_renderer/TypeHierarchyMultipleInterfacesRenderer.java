package de.heinerkuecker.type_hierarchy_multiple_interfaces_renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.heinerkuecker.class_with_generics_to_str.ClassDeclarationToStrWithGenerics;
import de.heinerkuecker.inner_or_nested_class_names_in_dot_notation.InnerOrNestedClassName;
import de.heinerkuecker.type_prefix.TypePrefix;

/**
 * Small tool for displaying multiple inheritance with interfaces in Java in ASCII art in console.
 *
 * @author Heiner K&uuml;cker
 */
public class TypeHierarchyMultipleInterfacesRenderer
{
    /**
     * Output in javadoc mode, yes or no.
     */
    public boolean javadocMode;

    /**
     * Output ascii art lines with HTML attribute title with type names as tooltip.
     */
    public boolean renderJavadocTooltips;

    /**
     * Output abstract and final modifier of class.
     */
    public boolean withAbstractOrFinal;

    /**
     * Output enum {@link Enum} as 'enum', not as 'final class'.
     */
    public boolean withEnum;

    /**
     * Output anonymous class as 'anonym class' and output local class as 'local class'.
     */
    public boolean withAnonymOrLocal;

    /**
     * Output type parameters of classes and interfaces.
     *
     * Only for console output, not for javadoc output.
     */
    public boolean withGenerics;

    /**
     * Output super class and super interfaces
     *
     * Output always with generics.
     *
     * Only for console output, not for javadoc output.
     */
    public boolean withSuperClassAndSuperInterfaces;

    /**
     * Output super class and super interfaces in multiple lines for better readability.
     *
     * Only for console output, not for javadoc output.
     */
    public boolean superClassAndSuperInterfacesMultiLined;

    /**
     * Classes, interfaces, enumerations to show.
     */
    public Class<?>[] classes;

    /**
     * Classes, interfaces, enumerations to exclude.
     */
    public final Set<Class<?>> excludes = new HashSet<>();

    private /*final*/ TypeHierarchyMultipleInterfacesRendererIndent indent;
    //new TypeHierarchyMultipleInterfacesRendererIndent(
    //        //parent
    //        //null ,
    //        //indentPrefix ,
    //        //deepClone(
    //        //        extenderAndImplementerMap ) ,
    //        //lineSeparatorStr
    //        "" ,
    //        //classes
    //        new Class<?>[ 0 ] );

    private Map<Class<?>, Set<Class<?>>> extenderAndImplementerMap;

    /**
     * Render output.
     *
     * @return output as string
     */
    public String render()
    {
        /*final Map<Class<?>, Set<Class<?>>>*/ this.extenderAndImplementerMap = createExtenderAndImplementerMap();

        final Set<Class<?>> roots = getRoots( this.extenderAndImplementerMap );

        final TypeHierarchyMultipleInterfacesRendererHierarchy[] hierarchies = new TypeHierarchyMultipleInterfacesRendererHierarchy[ roots.size() ];
        final Map<Class<?>, TypeHierarchyMultipleInterfacesRendererHierarchy> hierarchyMap = new HashMap<>();
        int i = 0;

        for ( final Class<?> root : roots )
        {
            hierarchies[ i++ ] =
                    createHierarchyRecursive(
                            // superClass
                            null ,
                            root ,
                            this.extenderAndImplementerMap ,
                            hierarchyMap );
        }

        sortRecursive( hierarchies );

        final StringBuilder buff = new StringBuilder();

        final String indentPrefix;
        if ( this.javadocMode )
        {
            indentPrefix = " * ";
            buff.append( indentPrefix );
            buff.append( "<pre>" ); // begin ascii art in html
            buff.append( '\n' );
        }
        else
        {
            indentPrefix = "";
        }

        //TypeHierarchyMultipleInterfacesRendererIndent indent =
        this.indent =
                new TypeHierarchyMultipleInterfacesRendererIndent(
                        this.withAbstractOrFinal ,
                        this.withEnum ,
                        this.withAnonymOrLocal ,
                        this.withGenerics ,
                        //renderJavadocTooltips
                        ( this.javadocMode ? this.renderJavadocTooltips : false ) ,
                        //parent
                        //null ,
                        //indentPrefix ,
                        deepClone(
                                this.extenderAndImplementerMap ) ,
                        //lineSeparatorStr
                        //"" ,
                        //classes
                        new Class<?>[ 0 ] );
        //indent.extenderAndImplementerMap =
        //        deepClone(
        //                extenderAndImplementerMap );

        final Set<Class<?>> alreadyRenderedClasses = new HashSet<>();

        // loop over roots
        boolean isFirstRootLoopRun = true;
        for ( final TypeHierarchyMultipleInterfacesRendererHierarchy hierarchy : hierarchies )
        {
            if ( ! isFirstRootLoopRun )
            {
                buff.append( indentPrefix );
                buff.append( rtrim( this.indent.lineSeparatorStr ) );
                buff.append( '\n' );
            }
            else
            {
                isFirstRootLoopRun = false;
            }

            alreadyRenderedClasses.add( hierarchy.clazz );

            if ( this.javadocMode )
            {
                buff.append( indentPrefix );
                buff.append( this.indent.getConnectedStr( Collections.emptySet() ) );

                buff.append( "{@link " ); // begin java doc link
                //buff.append( hierarchy.clazz.getName() );
                buff.append(
                        InnerOrNestedClassName.innerOrNestedClassName(
                                hierarchy.clazz ) );
            }
            else if ( withSuperClassAndSuperInterfaces &&
                    superClassAndSuperInterfacesMultiLined )
            {
                final String[] classStrLines =
                        classStrMultilined(
                                hierarchy.clazz ,
                                this.withAbstractOrFinal ,
                                this.withEnum ,
                                this.withAnonymOrLocal );

                for ( final String classStrLine : classStrLines )
                {
                    buff.append( indentPrefix );
                    buff.append( this.indent.getConnectedStr( Collections.emptySet() ) );

                    buff.append( classStrLine );
                    buff.append( '\n' );
                }
            }
            else
            {
                buff.append( indentPrefix );
                buff.append( this.indent.getConnectedStr( Collections.emptySet() ) );

                final String classStr =
                        //.getName()
                        classToStr(
                                hierarchy.clazz ,
                                this.withAbstractOrFinal ,
                                this.withEnum ,
                                this.withAnonymOrLocal ,
                                this.withGenerics ,
                                this.withSuperClassAndSuperInterfaces );

                buff.append( classStr );
                buff.append( '\n' );
            }

            if ( this.javadocMode )
            {
                buff.append( '}' ); // end java doc link
                buff.append( '\n' );
            }

            /*indent =*/ this.indent.add( hierarchy.clazz );
        }

        // loop over sub hierarchies
        for ( final TypeHierarchyMultipleInterfacesRendererHierarchy hierarchy : hierarchies )
        {
            if ( hierarchy.subHierarchies != null &&
                    hierarchy.subHierarchies.length > 0 )
            {
                appendRecursive(
                        buff ,
                        //indent ,
                        indentPrefix ,
                        alreadyRenderedClasses ,
                        hierarchy.subHierarchies );
            }
        }

        if ( this.javadocMode )
        {
            buff.append( indentPrefix );
            buff.append( "</pre>" ); // end ascii art in html
            buff.append( '\n' );

            buff.append( "Copy the console output into your javadoc comment\n" );
        }

        if ( ( ! javadocMode ) &&
                superClassAndSuperInterfacesMultiLined )
        {
            clearUnconnectedVerticalLines( buff );
        }

        return buff.toString();
    }

    private static Map<Class<?>, Set<Class<?>>> deepClone(
            final Map<Class<?>, Set<Class<?>>> extenderAndImplementerMapToClone )
    {
        final Map<Class<?>, Set<Class<?>>> clone = new HashMap<>();

        for ( final Entry<Class<?>, Set<Class<?>>> entry : extenderAndImplementerMapToClone.entrySet() )
        {
            clone.put(
                    entry.getKey() ,
                    new HashSet<>( entry.getValue() ) );
        }

        return clone;
    }

    private Map<Class<?>, Set<Class<?>>> createExtenderAndImplementerMap()
    {
        final Map<Class<?>, Set<Class<?>>> newExtenderAndImplementerMap = new HashMap<>();

        for ( final Class<?> clazz : this.classes )
        {
            if ( clazz.isArray() ||
                    clazz.isPrimitive() )
            {
                throw new IllegalArgumentException( String.valueOf( clazz ) );
            }

            putSuperClassAndInterfacesRecursiveInExtenderAndImplementerMap(
                    newExtenderAndImplementerMap ,
                    clazz );
        }

        checkForUnconnectedClass( newExtenderAndImplementerMap );

        return newExtenderAndImplementerMap;
    }

    private void checkForUnconnectedClass(
            final Map<Class<?>, Set<Class<?>>> extenderAndImplementerMapParam )
    {
        for ( final Class<?> clazz : this.classes )
        {
            if ( ! extenderAndImplementerMapParam.containsKey( clazz ) )
            {
                boolean isConnected = false;

                for ( final Entry<Class<?>, Set<Class<?>>> extenderAndImplementerEntry : extenderAndImplementerMapParam.entrySet() )
                {
                    if ( extenderAndImplementerEntry.getValue().contains( clazz ) )
                    {
                        isConnected = true;
                        break;
                    }
                }

                if ( ! isConnected )
                {
                    throw new IllegalArgumentException( "class has no connection: " + clazz );
                }
            }
        }
    }

    private void putSuperClassAndInterfacesRecursiveInExtenderAndImplementerMap(
            final Map<Class<?>, Set<Class<?>>> extenderAndImplementerMapToPutIn ,
            final Class<?> clazz )
    {
        if ( ! clazz.isInterface() )
        {
            final Class<?> superClass = clazz.getSuperclass();

            if ( superClass != null &&
                    ( ! this.excludes.contains( superClass ) ) )
            {
                putInExtenderAndImplementerMap(
                        extenderAndImplementerMapToPutIn ,
                        superClass ,
                        clazz );

                putSuperClassAndInterfacesRecursiveInExtenderAndImplementerMap(
                        extenderAndImplementerMapToPutIn ,
                        superClass );
            }
        }

        final Class<?>[] interfaces = clazz.getInterfaces();

        for ( final Class<?> interfaze : interfaces )
        {
            if ( ! this.excludes.contains( interfaze ) )
            {
                putInExtenderAndImplementerMap(
                        extenderAndImplementerMapToPutIn ,
                        interfaze ,
                        clazz );

                putSuperClassAndInterfacesRecursiveInExtenderAndImplementerMap(
                        extenderAndImplementerMapToPutIn ,
                        interfaze );
            }
        }
    }

    private static void putInExtenderAndImplementerMap(
            final Map<Class<?>, Set<Class<?>>> hierarchy ,
            final Class<?> superClassOrInterface ,
            final Class<?> clazz )
    {
        Set<Class<?>> superClassOrInterfaceSet = hierarchy.get( superClassOrInterface );

        if ( superClassOrInterfaceSet == null )
        {
            superClassOrInterfaceSet = new HashSet<>();

            hierarchy.put(
                    superClassOrInterface ,
                    superClassOrInterfaceSet );
        }

        superClassOrInterfaceSet.add( clazz );
    }

    private static Set<Class<?>> getRoots(
            final Map<Class<?>, Set<Class<?>>> extenderAndImplementerMap )
    {
        final Set<Class<?>> roots =
                // create clone of view to avoid ConcurrentModificationException
                new HashSet<>( extenderAndImplementerMap.keySet() );

        for ( final Set<Class<?>> subs : extenderAndImplementerMap.values() )
        {
            roots.removeAll( subs );
        }
        return roots;
    }

    private static TypeHierarchyMultipleInterfacesRendererHierarchy createHierarchyRecursive(
            final Class<?> superClass ,
            final Class<?> clazz ,
            final Map<Class<?>, Set<Class<?>>> extenderAndImplementerMap ,
            final Map<Class<?>, TypeHierarchyMultipleInterfacesRendererHierarchy> hierarchyMap )
    {
        final Set<Class<?>> extenderAndImplementerSet = extenderAndImplementerMap.get( clazz );

        final TypeHierarchyMultipleInterfacesRendererHierarchy[] subHierarchies;

        if ( extenderAndImplementerSet != null )
        {
            final List<TypeHierarchyMultipleInterfacesRendererHierarchy> subHierarchyList = new ArrayList<>();

            for ( final Class<?> subClass : extenderAndImplementerSet )
            {
                TypeHierarchyMultipleInterfacesRendererHierarchy subHierarchy = hierarchyMap.get( subClass );

                if ( subHierarchy == null )
                {
                    subHierarchy =
                            createHierarchyRecursive(
                                    clazz ,
                                    subClass ,
                                    extenderAndImplementerMap ,
                                    hierarchyMap );

                    hierarchyMap.put(
                            subClass ,
                            subHierarchy );
                }
                else
                {
                    subHierarchy.superClassAndInterfaces.add( clazz );
                }

                subHierarchyList.add( subHierarchy );
            }

            subHierarchies =
                    subHierarchyList.toArray( new TypeHierarchyMultipleInterfacesRendererHierarchy[ subHierarchyList.size() ] );
        }
        else
        {
            subHierarchies = null;
        }

        return new TypeHierarchyMultipleInterfacesRendererHierarchy(
                extenderAndImplementerMap ,
                superClass ,
                clazz ,
                subHierarchies );
    }

    private void sortRecursive(
            final TypeHierarchyMultipleInterfacesRendererHierarchy[] hierarchies )
    {
        Arrays.sort( hierarchies );

        for ( final TypeHierarchyMultipleInterfacesRendererHierarchy hierarchy : hierarchies )
        {
            if ( hierarchy.subHierarchies != null )
            {
                sortRecursive(
                        hierarchy.subHierarchies );
            }
        }
    }

    private void appendRecursive(
            final StringBuilder buff ,
            // /*not final*/ TypeHierarchyMultipleInterfacesRendererIndent indent ,
            final String indentPrefix ,
            final Set<Class<?>> alreadyRenderedClasses ,
            final TypeHierarchyMultipleInterfacesRendererHierarchy[] subHierarchies )
    {
        // loop over roots of sub hierarchies
        for ( final TypeHierarchyMultipleInterfacesRendererHierarchy subHierarchy : subHierarchies )
        {
            //if ( subHierarchy.clazz.equals( java.util.AbstractQueue.class ) )
            //{
            //    System.out.println( "debug break: " + subHierarchy.clazz ); // TODO only for debug
            //}

            if ( ! alreadyRenderedClasses.contains( subHierarchy.clazz ) )
            {
                if ( alreadyRenderedClasses.containsAll( subHierarchy.superClassAndInterfaces ) )
                {
                    alreadyRenderedClasses.add( subHierarchy.clazz );

                    buff.append( indentPrefix );
                    buff.append( rtrim( this.indent.lineSeparatorStr ) );
                    buff.append( '\n' );

                    if ( this.javadocMode )
                    {
                        buff.append( indentPrefix );
                        buff.append( this.indent.getConnectedStr( subHierarchy.superClassAndInterfaces ) );
                        this.indent.removeSubClassWithoutTrim( subHierarchy.clazz );

                        buff.append( "{@link " ); // begin java doc link
                        //buff.append( subHierarchy.clazz.getName() );
                        buff.append(
                                InnerOrNestedClassName.innerOrNestedClassName(
                                        subHierarchy.clazz ) );
                    }
                    else if ( withSuperClassAndSuperInterfaces &&
                            superClassAndSuperInterfacesMultiLined )
                    {
                        buff.append( indentPrefix );
                        buff.append( this.indent.getConnectedStr( subHierarchy.superClassAndInterfaces ) );
                        this.indent.removeSubClassWithoutTrim( subHierarchy.clazz );

                        final String[] classStrLines =
                                classStrMultilined(
                                        subHierarchy.clazz ,
                                        this.withAbstractOrFinal ,
                                        this.withEnum ,
                                        this.withAnonymOrLocal );

                        boolean isFirstLoopRun = true;
                        for ( final String classStrLine : classStrLines )
                        {
                            if ( isFirstLoopRun )
                            {
                                isFirstLoopRun = false;
                            }
                            else
                            {
                                buff.append( indentPrefix );
                                buff.append( this.indent.lineSeparatorStr );
                                buff.append( '|' );
                            }

                            buff.append( classStrLine );
                            buff.append( '\n' );
                        }
                    }
                    else
                    {
                        buff.append( indentPrefix );
                        buff.append( this.indent.getConnectedStr( subHierarchy.superClassAndInterfaces ) );
                        this.indent.removeSubClassWithoutTrim( subHierarchy.clazz );

                        final String classStr =
                                //.getName()
                                classToStr(
                                        subHierarchy.clazz ,
                                        this.withAbstractOrFinal ,
                                        this.withEnum ,
                                        this.withAnonymOrLocal ,
                                        this.withGenerics ,
                                        this.withSuperClassAndSuperInterfaces );

                        buff.append( classStr );
                        buff.append( '\n' );
                    }

                    if ( this.javadocMode )
                    {
                        buff.append( '}' ); // end java doc link
                        buff.append( '\n' );
                    }

                    if ( subHierarchy.subHierarchies != null &&
                            subHierarchy.subHierarchies.length > 0 )
                    {
                        /*indent =*/ this.indent.add( subHierarchy.clazz );
                    }
                    //else
                    //{
                    //    //indent.removeSubClass( subHierarchy.clazz );
                    //}
                    //this.indent.removeSubClass( subHierarchy.clazz );
                    this.indent.trim();
                }
            }

            // loop over sub sub hierarchies
            // Gib alle Untertypen aus, deren gesamte Obertypen ausgegeben wurden, so nahe wie m�glich
            // Output all subtypes whose total supertypes have been output, as close as possible
            for ( final TypeHierarchyMultipleInterfacesRendererHierarchy subHierarchy1 : subHierarchies )
            {
                //if ( subHierarchy.clazz.equals( java.util.AbstractQueue.class ) )
                //{
                //    System.out.println( "debug break: " + subHierarchy.clazz ); // TODO only for debug
                //}

                if ( alreadyRenderedClasses.contains( subHierarchy1.clazz ) )
                {
                    if ( alreadyRenderedClasses.containsAll( subHierarchy1.superClassAndInterfaces ) )
                    {
                        if ( subHierarchy1.subHierarchies != null &&
                                subHierarchy1.subHierarchies.length > 0 )
                        {
                            //TypeHierarchyMultipleInterfacesRendererIndent subIndent = indent.add( subHierarchy.clazz );

                            appendRecursive(
                                    buff ,
                                    //subIndent
                                    //indent ,
                                    indentPrefix ,
                                    alreadyRenderedClasses ,
                                    subHierarchy1.subHierarchies );
                        }
                    }
                }
            }

        }
    }

    static String classToStr(
            final Class<?> clazz ,
            final boolean withAbstractOrFinal ,
            final boolean withEnum ,
            final boolean withAnonymOrLocal ,
            final boolean withGenerics ,
            final boolean withSuperClassAndSuperInterfaces )
    {
        //final String abstractPrefix;
        //if ( ( ! clazz.isInterface() ) && Modifier.isAbstract( clazz.getModifiers() ) )
        //{
        //    abstractPrefix = "abstract ";
        //}
        //else if ( Modifier.isFinal( clazz.getModifiers() ) )
        //{
        //    abstractPrefix = "final ";
        //}
        //else
        //{
        //    abstractPrefix = "";
        //}
        //return abstractPrefix + clazz;

        final String typePrefix =
                TypePrefix.getTypePrefix(
                        clazz ,
                        withAbstractOrFinal ,
                        withEnum ,
                        withAnonymOrLocal );

        final String classNameAndTypeparams;

        if ( withSuperClassAndSuperInterfaces )
        {
            classNameAndTypeparams =
                    ClassDeclarationToStrWithGenerics.typeToStrTuple(
                            clazz ).getOneLineStr();
        }
        else if ( withGenerics )
        {
            classNameAndTypeparams =
                    ClassDeclarationToStrWithGenerics.typeWithTypeParametersToString(
                            clazz );
        }
        else
        {
            classNameAndTypeparams =
                    InnerOrNestedClassName.innerOrNestedClassName(
                            clazz );
        }

        return
                typePrefix +
                classNameAndTypeparams;
    }

    static String[] classStrMultilined(
            final Class<?> clazz ,
            final boolean withAbstractOrFinal ,
            final boolean withEnum ,
            final boolean withAnonymOrLocal )
    {
        final String classNameAndTypeparamsMultiLinedStr =
                ClassDeclarationToStrWithGenerics.typeToStrTuple(
                        clazz ).getTypeHierarchyMultipleInterfacesRendererMultiLineStr();

        final String classNameAndTypeparamsMultiLinedStrTabsToSpaces =
                classNameAndTypeparamsMultiLinedStr.replaceAll(
                        "\t" ,
                        "    " );

        final String[] lines =
                classNameAndTypeparamsMultiLinedStrTabsToSpaces.split(
                        // https://stackoverflow.com/questions/454908/split-java-string-by-new-line
                        "\\r?\\n" );

        final String typePrefix =
                TypePrefix.getTypePrefix(
                        clazz ,
                        withAbstractOrFinal ,
                        withEnum ,
                        withAnonymOrLocal );

        lines[ 0 ] = typePrefix + lines[ 0 ];

        return lines;
    }

    /**
     * Right trim.
     *
     * @param str
     * @return
     */
    private static String rtrim(
            final String str )
    {
        if ( str.isEmpty() )
        {
            return str;
        }

        int trimmedLength = str.length();
        for ( ; trimmedLength >= 0 ; --trimmedLength )
        {
            if ( ! Character.isWhitespace( str.charAt( trimmedLength - 1 ) ) )
            {
                break;
            }
        }

        return
                str.substring(
                        0 ,
                        trimmedLength );
    }

    private static void clearUnconnectedVerticalLines(
            final StringBuilder buff )
    {
        final HashSet<Character> belowLineConnectedChars =
                new HashSet<>(
                        Arrays.asList(
                                Character.valueOf( '|' ) ,
                                Character.valueOf( '+' ) ) );

        final String[] lines = buff.toString().split( "\n" );

        for ( int linesIndex = lines.length - 1 ; linesIndex >= 0 ; linesIndex-- )
        {
            int unconnectedVerticalLineCharFromIndex = 0;

            final StringBuilder lineBuff = new StringBuilder( lines[ linesIndex ] );

            int unconnectedVerticalLineCharIndex;
            while ( ( unconnectedVerticalLineCharIndex =
                    lineBuff.indexOf(
                    //str
                    "|" ,
                    //fromIndex
                    unconnectedVerticalLineCharFromIndex ) ) >= 0 )
            {
                if ( ( linesIndex == lines.length - 1 ) ||
                        lines[ linesIndex + 1 ].length() <= unconnectedVerticalLineCharIndex ||
                        ! belowLineConnectedChars.contains( lines[ linesIndex + 1 ].charAt( unconnectedVerticalLineCharIndex ) ) )
                {
                    lineBuff.replace(
                            //start
                            unconnectedVerticalLineCharIndex ,
                            //end
                            unconnectedVerticalLineCharIndex + 1 ,
                            //str
                            " " );
                }

                unconnectedVerticalLineCharFromIndex = unconnectedVerticalLineCharIndex + 1;
            }

            lines[ linesIndex ] = lineBuff.toString();
        }

        buff.setLength(
                //newLength
                0 );

        for ( final String line : lines )
        {
            buff.append( line );
            buff.append( '\n' );
        }

        // spike code only for last line
        //final int lastLineStartIndex =
        //        buff.lastIndexOf(
        //                "\n" ,
        //                //fromIndex
        //                buff.length() - 2 );
        //
        //if ( lastLineStartIndex >= 0 )
        //{
        //    int unconnectedVerticalLineCharIndex;
        //    while ( ( unconnectedVerticalLineCharIndex = buff.indexOf(
        //            //str
        //            "|" ,
        //            //fromIndex
        //            lastLineStartIndex ) ) >= 0 )
        //    {
        //        buff.replace(
        //                //start
        //                unconnectedVerticalLineCharIndex ,
        //                //end
        //                unconnectedVerticalLineCharIndex + 1 ,
        //                //str
        //                " " );
        //    }
        //}
    }

}
