package de.heinerkuecker.class_with_generics_to_str;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import de.heinerkuecker.inner_or_nested_class_names_in_dot_notation.InnerOrNestedClassName;

/**
 * Give type parameters in Java notation.
 *
 * @author Heiner K&uuml;cker
 */
public class TypeParametersToStr
{
    /**
     * Give type parameters in Java notation.
     *
     * @param clazz class to give type parameters as string
     * @return type parameters in Java notation
     */
    public static String typeParametersToString(
            final Class<?> clazz )
    {
        final TypeVariable<?>[] typeParameters = clazz.getTypeParameters();

        if ( TypeArgumentsToStr.arrayIsEmpty( typeParameters ) )
        {
            return "";
        }

        String typeParameterStr; // TODO StringBuilder
        typeParameterStr = "<";

        boolean isFirstTypeParametersLoopRun = true;
        for ( final TypeVariable<?> typeParameter : typeParameters )
        {
            if ( isFirstTypeParametersLoopRun )
            {
                isFirstTypeParametersLoopRun = false;
            }
            else
            {
                typeParameterStr += ", ";
            }

            typeParameterStr += typeParameter.getName();

            final Type[] bounds = typeParameter.getBounds();

            typeParameterStr +=
                    boundsToStr(
                            bounds );
        }

        typeParameterStr += ">";

        return typeParameterStr;
    }

    private static String boundsToStr(
            final Type[] bounds )
    {
        if ( TypeArgumentsToStr.arrayIsEmpty( bounds ) )
        {
            return "";
        }

        if ( bounds.length == 1 &&
                Object.class.equals( bounds[ 0 ] ) )
        {
            return "";
        }

        String boundsStr = " extends "; // TODO StringBuilder

        boolean isFirstBoundsLoopRun = true;

        for ( final Type bound : bounds )
        {
            if ( isFirstBoundsLoopRun )
            {
                isFirstBoundsLoopRun = false;
            }
            else
            {
                boundsStr += " & ";
            }

            //Class.class ,
            //GenericArrayType.class ,
            //ParameterizedType.class ,
            //TypeVariable.class ,
            // kann hier nicht vorkommen: WildcardType.class
            if ( bound instanceof ParameterizedType )
            {
                final ParameterizedType parameterizedTypeBound = (ParameterizedType) bound;

                boundsStr +=
                        InnerOrNestedClassName.innerOrNestedClassName(
                                (Class<?>) parameterizedTypeBound.getRawType() );

                                boundsStr +=
                                        TypeArgumentsToStr.typeArgumentsToStr(
                                                parameterizedTypeBound.getActualTypeArguments() );
            }
            else if ( bound instanceof TypeVariable )
            {
                final TypeVariable<?> typeVariableBound = (TypeVariable<?>) bound;

                boundsStr += typeVariableBound.getName();
            }
            //else if ( bound instanceof GenericArrayType )
            //{
            //    // TODO impossible in Java
            //    throw new RuntimeException( "not implemented " + bound + " " + bound.getClass() );
            //}
            else if ( bound instanceof Class )
            {
                final Class<?> classBound = (Class<?>) bound;

                boundsStr +=
                        //String.valueOf( classBound )
                        InnerOrNestedClassName.innerOrNestedClassName(
                                classBound );
            }
            else
            {
                throw new RuntimeException( "not implemented " + bound + " " + bound.getClass() );
            }
        }

        return boundsStr;
    }

}
