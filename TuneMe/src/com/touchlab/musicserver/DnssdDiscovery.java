package com.touchlab.musicserver;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class DnssdDiscovery {
	
	public interface DiscoveryChange {
		void discoveryChangeNotification();
	}
	WeakReference<DiscoveryChange> callback = null;

	private android.net.wifi.WifiManager.MulticastLock lock;

	private String type = "_tuneme._tcp.local.";
	private JmDNS jmdns = null;
	private ServiceListener listener = null;
	private ConcurrentHashMap<String, ServiceInfo> services;
	
	static DnssdDiscovery instance = null;
	public static synchronized DnssdDiscovery getInstance() {
		if (instance == null) {
			instance = new DnssdDiscovery();
		}
		return instance;		
	}
	
	private DnssdDiscovery() {
		
	}
	
	public void setNotificationCallback(DiscoveryChange callback) {
		this.callback = new WeakReference<DiscoveryChange>(callback);
	}
	
	public void setUp(Context context) {
		services = new ConcurrentHashMap<String, ServiceInfo>();
		android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
				.getSystemService(android.content.Context.WIFI_SERVICE);
		lock = wifi.createMulticastLock("mylockthereturn");
		lock.setReferenceCounted(true);
		lock.acquire();
		try {
			jmdns = JmDNS.create();
			jmdns.addServiceListener(type, listener = new ServiceListener() {

				public void serviceResolved(ServiceEvent ev) {
 					services.put(ev.getInfo().getQualifiedName(), ev.getInfo());
 					if (callback != null) {
 						if (callback.get() != null) {
 							callback.get().discoveryChangeNotification();
 						}
 					}
				}

				public void serviceRemoved(ServiceEvent ev) {
					services.remove(ev.getInfo().getQualifiedName());
					if (callback != null) {
 						if (callback.get() != null) {
 							callback.get().discoveryChangeNotification();
 						}
 					}
				}

				public void serviceAdded(ServiceEvent event) {
					// Required to force serviceResolved to be called again
					// (after the first search)
					jmdns.requestServiceInfo(event.getType(), event.getName(),
							1);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void publishUrl(String name, String description, String url) {
		ServiceInfo serviceInfo = ServiceInfo.create("_tuneme._tcp.local.",
				name, 8080, description);
		try {
			jmdns.unregisterAllServices();
			jmdns.registerService(serviceInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<ServiceInfo> getServicesInfo() {

		ArrayList<ServiceInfo> list = new ArrayList<ServiceInfo>();
		for (ServiceInfo si : services.values()) {
			list.add(si);
		}
		return list;
	}

	public void onStop() {
		if (jmdns != null) {
			if (listener != null) {
				jmdns.removeServiceListener(type, listener);
				listener = null;
			}
			jmdns.unregisterAllServices();
			try {
				jmdns.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			jmdns = null;
		}

		lock.release();

	}
}