/**
 * 
 */
package com.ensifera.animosity.craftirc;

import java.io.IOException;
import java.util.ArrayList;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

/**
 * @author morganm
 *
 */
public class Reconnector implements Runnable {
	private ArrayList<Minebot> instances;
	
	public Reconnector(ArrayList<Minebot> instances) {
//		System.out.println("Reconnector instantiated");
		this.instances = instances;
	}
	
	public void run() {
		CraftIRC.log.info("[CraftIRC] Reconnect running");
		
		for(Minebot m : instances) {
			m.disconnect();

			if( !m.isConnected() ) {
				try {
					Thread.sleep(10000);
					m.reconnect();
				}
				// nothing fancy with exceptions for now, just dump to syslog and admin can always
				// diagnose and run another reconnect if necessary.
				catch(InterruptedException e) {
					;
				}
				catch (NickAlreadyInUseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (IrcException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
