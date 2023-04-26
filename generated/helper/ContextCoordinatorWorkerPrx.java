//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.2
//
// <auto-generated>
//
// Generated from file `Helper.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package helper;

public interface ContextCoordinatorWorkerPrx extends com.zeroc.Ice.ObjectPrx
{
    default void addUser(String username)
    {
        addUser(username, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void addUser(String username, java.util.Map<String, String> context)
    {
        _iceI_addUserAsync(username, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> addUserAsync(String username)
    {
        return _iceI_addUserAsync(username, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> addUserAsync(String username, java.util.Map<String, String> context)
    {
        return _iceI_addUserAsync(username, context, false);
    }

    /**
     * @hidden
     * @param iceP_username -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_addUserAsync(String iceP_username, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "addUser", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeString(iceP_username);
                 }, null);
        return f;
    }

    default void deleteUser(String username)
    {
        deleteUser(username, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void deleteUser(String username, java.util.Map<String, String> context)
    {
        _iceI_deleteUserAsync(username, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> deleteUserAsync(String username)
    {
        return _iceI_deleteUserAsync(username, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> deleteUserAsync(String username, java.util.Map<String, String> context)
    {
        return _iceI_deleteUserAsync(username, context, false);
    }

    /**
     * @hidden
     * @param iceP_username -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_deleteUserAsync(String iceP_username, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "deleteUser", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeString(iceP_username);
                 }, null);
        return f;
    }

    default String searchInfo(String item)
    {
        return searchInfo(item, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String searchInfo(String item, java.util.Map<String, String> context)
    {
        return _iceI_searchInfoAsync(item, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> searchInfoAsync(String item)
    {
        return _iceI_searchInfoAsync(item, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> searchInfoAsync(String item, java.util.Map<String, String> context)
    {
        return _iceI_searchInfoAsync(item, context, false);
    }

    /**
     * @hidden
     * @param iceP_item -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.String> _iceI_searchInfoAsync(String iceP_item, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.String> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "searchInfo", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_item);
                 }, istr -> {
                     String ret;
                     ret = istr.readString();
                     return ret;
                 });
        return f;
    }

    default String[] searchItems(String username)
    {
        return searchItems(username, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String[] searchItems(String username, java.util.Map<String, String> context)
    {
        return _iceI_searchItemsAsync(username, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<String[]> searchItemsAsync(String username)
    {
        return _iceI_searchItemsAsync(username, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<String[]> searchItemsAsync(String username, java.util.Map<String, String> context)
    {
        return _iceI_searchItemsAsync(username, context, false);
    }

    /**
     * @hidden
     * @param iceP_username -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<String[]> _iceI_searchItemsAsync(String iceP_username, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<String[]> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "searchItems", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_username);
                 }, istr -> {
                     String[] ret;
                     ret = istr.readStringSeq();
                     return ret;
                 });
        return f;
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ContextCoordinatorWorkerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), ContextCoordinatorWorkerPrx.class, _ContextCoordinatorWorkerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ContextCoordinatorWorkerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), ContextCoordinatorWorkerPrx.class, _ContextCoordinatorWorkerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ContextCoordinatorWorkerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), ContextCoordinatorWorkerPrx.class, _ContextCoordinatorWorkerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ContextCoordinatorWorkerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), ContextCoordinatorWorkerPrx.class, _ContextCoordinatorWorkerPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static ContextCoordinatorWorkerPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, ContextCoordinatorWorkerPrx.class, _ContextCoordinatorWorkerPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static ContextCoordinatorWorkerPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, ContextCoordinatorWorkerPrx.class, _ContextCoordinatorWorkerPrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_context(java.util.Map<String, String> newContext)
    {
        return (ContextCoordinatorWorkerPrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_adapterId(String newAdapterId)
    {
        return (ContextCoordinatorWorkerPrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (ContextCoordinatorWorkerPrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (ContextCoordinatorWorkerPrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_invocationTimeout(int newTimeout)
    {
        return (ContextCoordinatorWorkerPrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_connectionCached(boolean newCache)
    {
        return (ContextCoordinatorWorkerPrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (ContextCoordinatorWorkerPrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_secure(boolean b)
    {
        return (ContextCoordinatorWorkerPrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (ContextCoordinatorWorkerPrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_preferSecure(boolean b)
    {
        return (ContextCoordinatorWorkerPrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (ContextCoordinatorWorkerPrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (ContextCoordinatorWorkerPrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_collocationOptimized(boolean b)
    {
        return (ContextCoordinatorWorkerPrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_twoway()
    {
        return (ContextCoordinatorWorkerPrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_oneway()
    {
        return (ContextCoordinatorWorkerPrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_batchOneway()
    {
        return (ContextCoordinatorWorkerPrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_datagram()
    {
        return (ContextCoordinatorWorkerPrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_batchDatagram()
    {
        return (ContextCoordinatorWorkerPrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_compress(boolean co)
    {
        return (ContextCoordinatorWorkerPrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_timeout(int t)
    {
        return (ContextCoordinatorWorkerPrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_connectionId(String connectionId)
    {
        return (ContextCoordinatorWorkerPrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default ContextCoordinatorWorkerPrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (ContextCoordinatorWorkerPrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::helper::ContextCoordinatorWorker";
    }
}
