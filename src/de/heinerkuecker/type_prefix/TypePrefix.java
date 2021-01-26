package de.heinerkuecker.type_prefix;

import java.lang.reflect.Modifier;

public class TypePrefix
{
    public static String getTypePrefix(
            final Class<?> clazz ,
            final boolean withAbstractOrFinal ,
            final boolean withEnum ,
            final boolean withAnonymOrLocal )
    {
        if ( clazz.isAnnotation() )
        {
            // annotation
            return "@interface ";
        }

        if ( clazz.isInterface() )
        {
            // interface is always abstract, no prefix abstract
            return "interface ";
        }

        if ( withEnum )
        {
            if ( isEnum( clazz ) )
            {
                // enum is always final, no prefix final
                return "enum ";
            }
        }

        // length of "abstract class "
        final int initialBuffLen = 15;
        final StringBuilder buff = new StringBuilder( initialBuffLen );

        if ( withAnonymOrLocal )
        {
            if ( clazz.isAnonymousClass() )
            {
                buff.append( "anonym " );
            }
            else if ( clazz.isLocalClass() )
            {
                buff.append( "local " );
            }
        }

        if ( withAbstractOrFinal )
        {
            final int modifiers = clazz.getModifiers();

            if ( Modifier.isAbstract( modifiers ) )
            {
                buff.append( "abstract " );
            }
            else if ( Modifier.isFinal( modifiers ) )
            {
                buff.append( "final " );
            }
        }

        buff.append( "class " );

        return buff.toString();
    }

    private static boolean isEnum(
            final Class<?> clazz )
    {
        if ( ( ! clazz.isInterface() ) &&
                ( ! clazz.isAnnotation() ) &&
                ( ! clazz.isPrimitive() ) &&
                ( ! clazz.isArray() ) )
        {
            // TODO test with java.lang.Object
            final Class<?> superclass = clazz.getSuperclass();
            if ( superclass != null &&
                    superclass.equals( Enum.class ) )
            {
                return true;
            }
        }
        return false;
    }

}
