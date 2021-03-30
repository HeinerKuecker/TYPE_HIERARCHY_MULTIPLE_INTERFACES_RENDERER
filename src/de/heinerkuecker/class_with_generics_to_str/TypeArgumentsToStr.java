package de.heinerkuecker.class_with_generics_to_str;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

import de.heinerkuecker.inner_or_nested_class_names_in_dot_notation.InnerOrNestedClassName;

/**
 *
 * @author Heiner K&uuml;cker
 */
public class TypeArgumentsToStr
{

    public static String typeArgumentsToStr(
            final Type[] actualTypeArguments )
    {
        if ( TypeArgumentsToStr.arrayIsEmpty( actualTypeArguments ) )
        {
            return "";
        }

        String actualTypeArgumentsStr = ""; // TODO StringBuilder

        for ( final Type actualTypeArgument : actualTypeArguments )
        {
            if ( ! actualTypeArgumentsStr.isEmpty() )
            {
                actualTypeArgumentsStr += ", ";
            }

            actualTypeArgumentsStr +=
                    typeArgumentToStr(
                            actualTypeArgument );
        }

        return "<" + actualTypeArgumentsStr + ">";
    }

    public static String typeArgumentToStr(
            final Type actualTypeArgument )
    {
        String actualTypeArgumentStr = ""; // TODO StringBuilder

        //Class.class ,
        //GenericArrayType.class ,
        //ParameterizedType.class ,
        //TypeVariable.class ,
        //WildcardType.class
        if ( actualTypeArgument instanceof ParameterizedType )
        {
            final ParameterizedType parameterizedTypeActualTypeArgument = (ParameterizedType) actualTypeArgument;

            actualTypeArgumentStr +=
                    parameterizedTypeActualTypeArgument.getRawType();

            actualTypeArgumentStr +=
                    typeArgumentsToStr(
                            parameterizedTypeActualTypeArgument.getActualTypeArguments() );
        }
        else if ( actualTypeArgument instanceof GenericArrayType )
        {
            final GenericArrayType genericArrayTypeActualTypeArgument = (GenericArrayType) actualTypeArgument;

            final Type genericComponentType = genericArrayTypeActualTypeArgument.getGenericComponentType();

            actualTypeArgumentStr +=
                    typeArgumentToStr(
                            genericComponentType );

            actualTypeArgumentStr += "[]";
        }
        else if ( actualTypeArgument instanceof TypeVariable )
        {
            final TypeVariable<?> typeVariableActualTypeArgument = (TypeVariable<?>) actualTypeArgument;

            actualTypeArgumentStr += typeVariableActualTypeArgument.getName();

            //if ( ! ArrayUtil.isEmpty( typeVariableActualTypeArgument.getBounds() ) )
            //{
            //    actualTypeArgumentsStr += " extends ";
            //
            //    actualTypeArgumentsStr +=
            //            boundsToStr(
            //                    typeVariableActualTypeArgument.getBounds() );
            //}
        }
        else if ( actualTypeArgument instanceof Class )
        {
            final Class<?> classActualTypeArgument = (Class<?>) actualTypeArgument;

            if ( classActualTypeArgument.isArray() )
            {
                final Class<?> componentType = classActualTypeArgument.getComponentType();

                actualTypeArgumentStr +=
                        typeArgumentToStr(
                                componentType );

                actualTypeArgumentStr += "[]";
            }
            else
            {
                //throw new RuntimeException( "not implemented " + actualTypeArgument + " " + actualTypeArgument.getClass() );

                actualTypeArgumentStr +=
                        InnerOrNestedClassName.innerOrNestedClassName(
                                classActualTypeArgument );
            }
        }
        else if ( actualTypeArgument instanceof WildcardType )
        {
            final WildcardType wildcardActualTypeArgument = (WildcardType) actualTypeArgument;

            final Type[] lowerBounds = wildcardActualTypeArgument.getLowerBounds();

            final Type[] upperBounds = wildcardActualTypeArgument.getUpperBounds();

            if ( ! arrayIsEmpty( lowerBounds ) )
            {
                //throw new RuntimeException( "not implemented " + actualTypeArgument + " " + actualTypeArgument.getClass() );
                actualTypeArgumentStr += "? super ";

                if ( lowerBounds.length != 1 )
                {
                    throw new IllegalStateException( Arrays.toString( lowerBounds ) );
                }

                actualTypeArgumentStr +=
                        typeArgumentToStr(
                                lowerBounds[ 0 ] );
            }
            else if (
                    upperBounds.length > 0 &&
                    ( upperBounds.length != 1 ||
                    ( ! upperBounds[ 0 ].equals( Object.class ) ) ) )
            {
                //throw new RuntimeException( "not implemented " + actualTypeArgument + " " + actualTypeArgument.getClass() );

                if ( upperBounds.length > 1 )
                {
                    // not supported by Java
                    throw new IllegalStateException( Arrays.toString( upperBounds ) );
                }

                actualTypeArgumentStr += "? extends ";

                actualTypeArgumentStr +=
                        typeArgumentToStr(
                                upperBounds[ 0 ] );
            }
            else
            {
                //throw new RuntimeException( "not implemented " + actualTypeArgument + " " + actualTypeArgument.getClass() );
                actualTypeArgumentStr += "?";
            }
        }
        else
        {
            throw new RuntimeException( "not implemented " + actualTypeArgument + " " + actualTypeArgument.getClass() );
        }

        return actualTypeArgumentStr;
    }

    static boolean arrayIsEmpty(
            final Object[] array )
    {
        return
                array == null ||
                array.length == 0;
    }

}
