package de.heinerkuecker.class_with_generics_to_str;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

import de.heinerkuecker.inner_or_nested_class_names_in_dot_notation.InnerOrNestedClassName;

/**
 * Give class signature with type parameters in Java notation.
 *
 * @author Heiner K&uuml;cker
 */
public class ClassDeclarationToStrWithGenerics
{
    /**
     * Give class signature with type parameters in Java notation.
     *
     * @param clazz
     * 	for this Java class the signature is to be returned as a string
     *  für diese Java-Klasse soll die Signatur als String zurückgegeben werden.
     *
     * @return class signature with type parameters in Java notation
     */
    public static String typeWithTypeParametersToString(
            final Class<?> clazz )
    {
        return
                InnerOrNestedClassName.innerOrNestedClassName(
                        clazz ) +
                TypeParametersToStr.typeParametersToString(
                        clazz );
    }

    public static String typeWithTypeArgumentsToString(
            final Type type )
    {
        String result = ""; // TODO StringBuilder
        if ( type instanceof Class )
        {
            final Class<?> clazz = (Class<?>) type;

            if ( clazz.isArray() )
            {
                result +=
                        typeWithTypeArgumentsToString(
                                clazz.getComponentType() ) +
                        "[]";
            }
            else
            {
                result +=
                        InnerOrNestedClassName.innerOrNestedClassName(
                                clazz );
            }
        }
        else if ( type instanceof ParameterizedType )
        {
            final ParameterizedType parameterizedType = (ParameterizedType) type;

            result +=
                    InnerOrNestedClassName.innerOrNestedClassName(
                            (Class<?>) parameterizedType.getRawType() ) +
                    TypeArgumentsToStr.typeArgumentsToStr(
                            parameterizedType.getActualTypeArguments() );
        }
        else if ( type instanceof TypeVariable )
        {
            final TypeVariable<?> typeVariableBound = (TypeVariable<?>) type;

            result += typeVariableBound.getName();
        }
        else if ( type instanceof GenericArrayType )
        {
            throw new RuntimeException( "not implemented " + type + " " + type.getClass() );
        }
        else
        {
            throw new RuntimeException( "not implemented " + type + " " + type.getClass() );
        }

        return result;
    }

    /**
     * Tuple class for class declaration string
     * with super class string and
     * super interfaces string.
     */
    public static class ClassDeclarationStrs
    {
        public final boolean isInterface;

        public final String classDeclarationStr;

        public final String superClassDeclarationStr;

        public final String[] superInterfacesDeclarationStrs;

        /**
         * Constructor.
         *
         * @param isInterface
         * @param classDeclarationStr
         * @param superClassDeclarationStr
         * @param superInterfacesDeclarationStrs
         */
        public ClassDeclarationStrs(
                final boolean isInterface ,
                final String classDeclarationStr ,
                final String superClassDeclarationStr ,
                final String[] superInterfacesDeclarationStrs )
        {
            this.isInterface = isInterface;
            this.classDeclarationStr = classDeclarationStr;
            this.superClassDeclarationStr = superClassDeclarationStr;
            this.superInterfacesDeclarationStrs = superInterfacesDeclarationStrs;
        }

        public String getOneLineStr()
        {
            String result = classDeclarationStr; // TODO StringBuilder

            if ( superClassDeclarationStr != null )
            {
                result += " extends " + superClassDeclarationStr;
            }

            if ( this.superInterfacesDeclarationStrs != null &&
                    this.superInterfacesDeclarationStrs.length > 0 )
            {
                if ( isInterface )
                {
                    result += " extends ";
                }
                else
                {
                    result += " implements ";
                }

                boolean isFirstRun = true;
                for ( final String superInterfacesDeclarationStr : superInterfacesDeclarationStrs )
                {
                    if ( isFirstRun )
                    {
                        isFirstRun = false;
                    }
                    else
                    {
                        result += ", ";
                    }
                    result += superInterfacesDeclarationStr;
                }
            }

            return result;
        }

        public String getMultiLineStr()
        {
            String result = classDeclarationStr; // TODO StringBuilder

            if ( superClassDeclarationStr != null )
            {
                result += "\nextends " + superClassDeclarationStr;
            }

            if ( this.superInterfacesDeclarationStrs != null &&
                    this.superInterfacesDeclarationStrs.length > 0 )
            {
                if ( isInterface )
                {
                    result += "\nextends ";
                }
                else
                {
                    result += "\nimplements ";
                }

                boolean isFirstRun = true;
                for ( final String superInterfacesDeclarationStr : superInterfacesDeclarationStrs )
                {
                    if ( isFirstRun )
                    {
                        isFirstRun = false;
                    }
                    else
                    {
                        result += ", ";
                    }
                    result += superInterfacesDeclarationStr;
                }
            }

            return result;
        }

        /**
         * Special multiple line format for <a href="https://github.com/HeinerKuecker/TYPE_HIERARCHY_MULTIPLE_INTERFACES_RENDERER">HeinerKuecker TYPE_HIERARCHY_MULTIPLE_INTERFACES_RENDERER on GitHub</a>.
         *
         * @return special format
         */
        public String getTypeHierarchyMultipleInterfacesRendererMultiLineStr()
        {
            String result = classDeclarationStr; // TODO StringBuilder

            if ( superClassDeclarationStr != null )
            {
                result += "\n\textends " + superClassDeclarationStr;
            }

            if ( this.superInterfacesDeclarationStrs != null &&
                    this.superInterfacesDeclarationStrs.length > 0 )
            {
                if ( isInterface )
                {
                    result += "\n\textends ";
                }
                else
                {
                    if ( this.superInterfacesDeclarationStrs.length > 1 )
                    {
                        result += "\n\timplements\n\t\t";
                    }
                    else
                    {
                        result += "\n\timplements ";
                    }
                }

                boolean isFirstRun = true;
                for ( final String superInterfacesDeclarationStr : superInterfacesDeclarationStrs )
                {
                    if ( isFirstRun )
                    {
                        isFirstRun = false;
                    }
                    else
                    {
                        result += ",\n\t\t";
                    }
                    result += superInterfacesDeclarationStr;
                }
            }

            return result;
        }

    }

    /**
     * Give class signature with type parameters in Java notation.
     *
     * @param clazz
     * 	for this Java class the signature is to be returned as a string
     *  für diese Java-Klasse soll die Signatur als String zurückgegeben werden.
     *
     * @return class signature with type parameters in Java notation
     */
    public static ClassDeclarationStrs typeToStrTuple(
            final Class<?> clazz )
    {
        final boolean isInterface = clazz.isInterface();

        final String classDeclarationStr =
                InnerOrNestedClassName.innerOrNestedClassName(
                        clazz ) +
                TypeParametersToStr.typeParametersToString(
                        clazz );

        final String superClassDeclarationStr;

        {
            final Type genericSuperclass = clazz.getGenericSuperclass();

            if ( genericSuperclass == null ||
                    Object.class.equals( genericSuperclass ) )
            {
                superClassDeclarationStr = null;
            }
            else if ( genericSuperclass instanceof Class )
            {
                superClassDeclarationStr =
                        InnerOrNestedClassName.innerOrNestedClassName(
                                (Class<?>) genericSuperclass );
            }
            else if ( genericSuperclass instanceof ParameterizedType )
            {
                final ParameterizedType parameterizedSuperClass = (ParameterizedType) genericSuperclass;

                superClassDeclarationStr =
                        InnerOrNestedClassName.innerOrNestedClassName(
                                (Class<?>) parameterizedSuperClass.getRawType() ) +
                        TypeArgumentsToStr.typeArgumentsToStr(
                                parameterizedSuperClass.getActualTypeArguments() );
            }
            else
            {
                throw new RuntimeException( "not implemented " + genericSuperclass + " " + genericSuperclass.getClass() );
            }
        }


        final String[] superInterfacesDeclarationStrs;

        final Type[] genericSuperInterfaces = clazz.getGenericInterfaces();

        if ( genericSuperInterfaces == null ||
                genericSuperInterfaces.length == 0 )
        {
            superInterfacesDeclarationStrs = null;
        }
        else
        {
            superInterfacesDeclarationStrs = new String[ genericSuperInterfaces.length ];

            for ( int i = 0 ; i < genericSuperInterfaces.length ; ++i )
            {
                final Type genericSuperInterface = genericSuperInterfaces[ i ];

                final String superInterfaceDeclarationStr;

                if ( genericSuperInterface instanceof Class )
                {
                    superInterfaceDeclarationStr =
                            InnerOrNestedClassName.innerOrNestedClassName(
                                    (Class<?>) genericSuperInterface );
                }
                else if ( genericSuperInterface instanceof ParameterizedType )
                {
                    final ParameterizedType parameterizedSuperInterface = (ParameterizedType) genericSuperInterface;

                    superInterfaceDeclarationStr =
                            InnerOrNestedClassName.innerOrNestedClassName(
                                    (Class<?>) parameterizedSuperInterface.getRawType() ) +
                            TypeArgumentsToStr.typeArgumentsToStr(
                                    parameterizedSuperInterface.getActualTypeArguments() );
                }
                else
                {
                    throw new RuntimeException( "not implemented " + genericSuperInterface + " " + genericSuperInterface.getClass() );
                }

                superInterfacesDeclarationStrs[ i ] = superInterfaceDeclarationStr;
            }
        }

        return
                new ClassDeclarationStrs(
                        isInterface ,
                        classDeclarationStr ,
                        superClassDeclarationStr ,
                        superInterfacesDeclarationStrs );
    }


    public static void main(String[] args)
    {
        // TODO convert to unit test
        ClassDeclarationStrs typeToStrTuple =
                typeToStrTuple(
                        ArrayList.class );

        System.out.println( typeToStrTuple.getOneLineStr() );

        System.out.println();

        System.out.println( typeToStrTuple.getMultiLineStr() );

        System.out.println();

        System.out.println( typeToStrTuple.getTypeHierarchyMultipleInterfacesRendererMultiLineStr() );
    }
}
