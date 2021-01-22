package de.heinerkuecker.type_hierarchy_multiple_interfaces;

import static de.heinerkuecker.type_hierarchy_multiple_interfaces.TypeHierarchyMultipleInterfacesRenderer.classToStr;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 *
 * @author Heiner K&uuml;cker
 */
class TypeHierarchyMultipleInterfacesRendererHierarchy
implements Comparable<TypeHierarchyMultipleInterfacesRendererHierarchy>
{
    // reference to global data structure
    final Map<Class<?>, Set<Class<?>>> extenderAndImplementerMap;

    final Set<Class<?>> superClassAndInterfaces = new HashSet<>();

    final Class<?> clazz;

    final TypeHierarchyMultipleInterfacesRendererHierarchy[] subHierarchies;

    /**
     * Constructor.
     *
     * @param clazz
     * @param subHierarchies
     */
    TypeHierarchyMultipleInterfacesRendererHierarchy(
            final Map<Class<?>, Set<Class<?>>> extenderAndImplementerMap ,
            final Class<?> superClass ,
            final Class<?> clazz ,
            final TypeHierarchyMultipleInterfacesRendererHierarchy[] subHierarchies )
    {
        this.extenderAndImplementerMap = extenderAndImplementerMap;

        if ( superClass != null )
        {
            this.superClassAndInterfaces.add( superClass );
        }

        this.clazz = clazz;
        this.subHierarchies = subHierarchies;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TypeHierarchyMultipleInterfacesRendererHierarchy other = (TypeHierarchyMultipleInterfacesRendererHierarchy) obj;
        return Objects.equals(clazz, other.clazz);
    }

    @Override
    public int compareTo(
            final TypeHierarchyMultipleInterfacesRendererHierarchy o )
    {
        //if ( this.isSuperClassorInterfaceOf( o.clazz ) )
        //{
        //    return 1;
        //}
        //if ( o.isSuperClassorInterfaceOf( this.clazz ) )
        //{
        //    return -1;
        //}

        //final int thisSubHierarchiesCount = getSubHierarchiesCount();
        //final int otherSubHierarchiesCount = o.getSubHierarchiesCount();
        //if ( thisSubHierarchiesCount < otherSubHierarchiesCount )
        //{
        //    return 1;
        //}
        //if ( thisSubHierarchiesCount > otherSubHierarchiesCount )
        //{
        //    return -1;
        //}

        final int thisSuperHierarchiesCount = superClassAndInterfaces.size();
        final int otherSuperHierarchiesCount = o.superClassAndInterfaces.size();
        if ( thisSuperHierarchiesCount < otherSuperHierarchiesCount )
        {
            return 1;
        }
        if ( thisSuperHierarchiesCount > otherSuperHierarchiesCount )
        {
            return -1;
        }

        // Hier wird ausgenutzt, dass die to string Methode von Klassen den Prefix class liefert und so Klassen vor Interfaces einsortiert werden
        // This makes use of the fact that the to string method of classes supplies the prefix class and so classes are sorted in front of interfaces
        //return this.clazz.toString().compareTo( o.clazz.toString() );
        final String thisClassStr =
                classToStr(
                        this.clazz ,
                        false ,
                        false );

        final String otherClassStr =
                classToStr(
                        o.clazz ,
                        false ,
                        false );

        return thisClassStr.compareTo( otherClassStr );
    }

    @Override
    public String toString()
    {
        return
                //this.getClass().getSimpleName() + "[" +
                "clazz=" + clazz + ", " +
                "superClassAndInterfaces=" + superClassAndInterfaces + ", " +
                "subHierarchies=" + Arrays.toString(subHierarchies) +
                //"]"
                "";
    }

    private boolean isSuperClassorInterfaceOf(
            final Class<?> otherClass )
    {
        return
                isSuperClassorInterfaceOf(
                        this.clazz ,
                        otherClass );
    }

    /* TODO private*/ boolean isSuperClassorInterfaceOf(
            final Class<?> thisClass ,
            final Class<?> otherClass )
    {
        //if ( thisClass.equals( Number.class ) &&
        //        otherClass.equals( short.class ) )
        //{
        //    System.out.println( "debug break" ); // TODO only for debug
        //}

        final Set<Class<?>> extenderAndImplementer = extenderAndImplementerMap.get( thisClass );

        if ( extenderAndImplementer != null )
        {
            for ( final Class<?> extenderOrImplementer : extenderAndImplementer )
            {
                if ( extenderOrImplementer.equals( otherClass ) )
                {
                    return true;
                }

                if ( isSuperClassorInterfaceOf(
                                extenderOrImplementer ,
                                otherClass ) )
                {
                    return true;
                }
            }
        }

        return false;
    }

    private int getSubHierarchiesCount()
    {
        if ( this.subHierarchies == null )
        {
            return 0;
        }
        return this.subHierarchies.length;
    }

}