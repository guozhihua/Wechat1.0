package com.weixin.utils.jdbc.cfg;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

//all internal maps should be HashMaps (the implementation presumes HashMaps)

public class NamedScope
{
    HashMap props;
    HashMap userNamesToOverrides;
    HashMap extensions;

    public HashMap getProps() {
        return props;
    }

    public HashMap getUserNamesToOverrides() {
        return userNamesToOverrides;
    }

    public HashMap getExtensions() {
        return extensions;
    }

    public NamedScope()

    {
        this.props                = new HashMap();
        this.userNamesToOverrides = new HashMap();
        this.extensions           = new HashMap();
    }

    public NamedScope(HashMap props, HashMap userNamesToOverrides, HashMap extensions)
    {
        this.props                = props;
        this.userNamesToOverrides = userNamesToOverrides;
        this.extensions           = extensions;
    }

    public  NamedScope mergedOver( NamedScope underScope )
    {
        HashMap mergedProps = (HashMap) underScope.props.clone();
        mergedProps.putAll( this.props );

        HashMap mergedUserNamesToOverrides = mergeUserNamesToOverrides( this.userNamesToOverrides, underScope.userNamesToOverrides );

        HashMap mergedExtensions = mergeExtensions( this.extensions, underScope.extensions );

        return new NamedScope( mergedProps, mergedUserNamesToOverrides, mergedExtensions );
    }

    public static HashMap mergeExtensions( HashMap over, HashMap under )
    {
        HashMap out = (HashMap) under.clone();
        out.putAll( over );
        return out;
    }

    public static HashMap mergeUserNamesToOverrides( HashMap over, HashMap under )
    {
        HashMap out = (HashMap) under.clone();

        HashSet underUserNames = new HashSet( under.keySet() );
        HashSet overUserNames = new HashSet( over.keySet() );

        HashSet newUserNames = (HashSet) overUserNames.clone();
        newUserNames.removeAll( underUserNames );

        for ( Iterator ii = newUserNames.iterator(); ii.hasNext(); )
        {
            String name = (String) ii.next();
            out.put( name, ((HashMap) over.get( name )).clone() );
        }

        HashSet mergeUserNames = (HashSet) overUserNames.clone();
        mergeUserNames.retainAll( underUserNames );

        for ( Iterator ii = mergeUserNames.iterator(); ii.hasNext(); )
        {
            String name = (String) ii.next();
            ((HashMap) out.get(name)).putAll( (HashMap) over.get( name ) );
        }

        return out;
    }
}
