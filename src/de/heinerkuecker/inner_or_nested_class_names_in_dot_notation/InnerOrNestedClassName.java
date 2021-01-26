package de.heinerkuecker.inner_or_nested_class_names_in_dot_notation;

/**
 * Klasse zum Ausgeben des Namens von inneren und geschachtelten Klassen in der Java-Punkt-Schreibweise.
 *
 * Class for displaying the names of inner and nested classes in Java dot notation.
 *
 * @author Heiner K&uuml;cker
 */
public class InnerOrNestedClassName
{
    /**
     * Methode zum Ausgeben des Namens von inneren und geschachtelten Klassen in der Java-Punkt-Schreibweise.
     *
     * Method for displaying the names of inner and nested classes in Java dot notation.
     *
     * @param clazz class to display name as string
     * @return string in Java dot notation
     */
    public static String innerOrNestedClassName(
            final Class<?> clazz )
    {
        if ( clazz.isSynthetic() )
        {
            throw new IllegalArgumentException( String.valueOf( clazz ) );
        }

        if ( clazz.isPrimitive() )
        {
            return clazz.getName();
        }

        final StringBuilder innerOrNestedClassNameBuff = new StringBuilder();

        final String packageName = clazz.getPackageName();

        if ( ! packageName.isEmpty() )
        {
            innerOrNestedClassNameBuff.append( packageName );

            innerOrNestedClassNameBuff.append( '.' );
        }

        final StringBuilder enclosingClassNamesBuff = new StringBuilder();

        Class<?> enclosingClass = clazz.getEnclosingClass();

        while ( enclosingClass != null )
        {
            if ( enclosingClass.isAnonymousClass() ||
                    enclosingClass.isSynthetic() )
            {
                throw new IllegalArgumentException( String.valueOf( clazz ) );
            }

            if ( enclosingClassNamesBuff.length() > 0 )
            {
                enclosingClassNamesBuff.insert( 0 , '.' );
            }

            enclosingClassNamesBuff.insert( 0 , enclosingClass.getSimpleName() );

            enclosingClass = enclosingClass.getEnclosingClass();
        }

        if ( enclosingClassNamesBuff.length() > 0 )
        {
            enclosingClassNamesBuff.append( '.' );
        }


        innerOrNestedClassNameBuff.append( enclosingClassNamesBuff.toString() );

        if ( clazz.isAnonymousClass() ||
                clazz.isLocalClass() )
        {
            final String anonymousOrLocalClassRawName = clazz.getName();

            //System.out.println( anonymousOrLocalClassRawName );

            final String anonymousOrLocalClassSimpleName =
                    anonymousOrLocalClassRawName.substring(
                    //beginIndex
                    anonymousOrLocalClassRawName.lastIndexOf( '$' ) + 1 );

            innerOrNestedClassNameBuff.append( anonymousOrLocalClassSimpleName );
        }
        else
        {
            innerOrNestedClassNameBuff.append( clazz.getSimpleName() );
        }

        return innerOrNestedClassNameBuff.toString();
    }

}
