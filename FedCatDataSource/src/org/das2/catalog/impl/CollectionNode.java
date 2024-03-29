/* Copyright (C) 2019 Chris Piker 
 *
 * This file is part of the das2 Core library.
 *
 * das2 is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public Library License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 
 * USA
 */
package org.das2.catalog.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.das2.util.LoggerManager;
import org.das2.util.monitor.ProgressMonitor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.das2.catalog.DasResolveException;
import org.das2.catalog.DasDirNode;
import org.das2.catalog.DasProp;

/** Collection Nodes group data sources that contain roughly equivalent data.
 *
 * There are many different ways to supply the same data.  Das2 servers,
 * HAPI servers, CDF file collections, SQL databases, one-off services, etc. etc.
 * This node type defines a collection of data.  It has provisions for a scientific
 * contact, and a rough description of a dataset but does not provide access methods.  
 * Those are provided by child nodes.
 * 
 * FIXME: Add reference to Collection node JSON format documentation or schema once
 * it exists.
 * 
 * @author cwp
 */
class CollectionNode extends AbstractDirNode 
{
	private static final Logger LOGGER = LoggerManager.getLogger("das2.catalog.collection" );
	
	JSONObject data;
	static final String TYPE = "Collection";
	
	// Static names for important data dictionary keys
	static final String KEY_CATALOG = "sources";
	static final String KEY_TYPE    = "type";
	static final String KEY_NAME    = "name";
	static final String KEY_URLS    = "urls";
	static final String KEY_SEPARATOR = "separator";
	static final String KEY_TITLE   = "title";
	static final String KEY_VERSION = "version";
	static final String KEY_SCI_CONTACT = "sci_contacts";
	static final String KEY_UNTIS = "units";
	static final String KEY_CONVETION = "convertion";
	static final String KEY_COORDS = "coordinates";
	static final String KEY_DATA = "data";
	
	CollectionNode(DasDirNode parent, String name, List<String> lUrls)
	{
		super(parent, name, lUrls);
		data = null;
	}
	
	@Override
	public String type() { return TYPE; }

	@Override
	public boolean isSrc() { return false; }
	
	@Override
	public boolean isDir(){ return true; }
	
	@Override
	public boolean isInfo(){ return false; }
	
	@Override
	public boolean isLoaded(){ return (data != null); }
	
	@Override
	public DasProp prop(String sFragment, Object oDefault){
		return JsonUtil.prop(data, sFragment, oDefault);
	}

	@Override
	public DasProp prop(String sFragment){
		return JsonUtil.prop(data, sFragment);
	}

	protected void initFromJson(JSONObject jo) throws JSONException, ParseException{

		if(! jo.getString(KEY_TYPE).equals(TYPE))
			throw new ParseException("Node type missing or not equal to " + TYPE, -1);
		
		if(jo.has(KEY_CATALOG)){
			JSONObject cat = jo.getJSONObject(KEY_CATALOG);
			Iterator<String> keys = cat.sortedKeys();
			while(keys.hasNext()){
				String sChildId = keys.next();
				JSONObject joChild = cat.getJSONObject(sChildId);
				
				String sChildType = joChild.getString(KEY_TYPE);  // Can't be null				
				String sChildName = joChild.optString(KEY_NAME, null);
				JSONArray jaLocs = joChild.optJSONArray(KEY_URLS);
				List<String> lChildLocs = null;
				if(jaLocs != null){
					lChildLocs = new ArrayList<>();
					for(int i = 0; i < jaLocs.length(); ++i){
						lChildLocs.add(jaLocs.getString(i));
					}
				}
				// Make the right kind of child
				AbstractNode child = NodeFactory.newNode(
					sChildType, this, sChildName, lChildLocs
				);
				
				dSubNodes.put(sChildId, child);
			}
		}
		
		// Work around odd implementation for the JSON library, see note in CatalogNode.
		if(jo.has(KEY_SEPARATOR)){
			if(jo.isNull(KEY_SEPARATOR)) sSep = "";
			else sSep = jo.getString(DEFAULT_PATH_SEP);
		}
		
		data = jo;
	}
	
	protected void mergeFromJson(JSONObject jo) throws JSONException, ParseException
	{
		if(! jo.getString(KEY_TYPE).equals(TYPE))
			throw new ParseException("Node type missing or not equal to " + TYPE, -1);
		
		// Add extra child objects and/or extra locations for existing child objects
		if(jo.has(KEY_CATALOG)){
			JSONObject cat = jo.getJSONObject(KEY_CATALOG);
			Iterator<String> keys = cat.sortedKeys();
			while(keys.hasNext()){
				String sChildId = keys.next();
				JSONObject joChild = cat.getJSONObject(sChildId);
				
				String sChildType = joChild.getString(KEY_TYPE);  // Can't be null				
				String sChildName = joChild.optString(KEY_NAME, null);
				JSONArray jaLocs = joChild.optJSONArray(KEY_URLS);
				List<String> lChildLocs = null;
				if(jaLocs != null){
					lChildLocs = new ArrayList<>();
					for(int i = 0; i < jaLocs.length(); ++i){
						lChildLocs.add(jaLocs.getString(i));
					}
				}
				
				AbstractNode child;
				
				if(!dSubNodes.containsKey(sChildName)){
					// If this child doesn't exist, add it...
					
					child = NodeFactory.newNode(
						sChildType, this, sChildName, lChildLocs
					);
					dSubNodes.put(sChildId, child);
				}
				else{
					// already have this child, but maybe there are new locations
					
					child = dSubNodes.get(sChildName);
					for(String sAvail: lChildLocs){
						boolean bNewLoc = true;
						for(NodeDefLoc has: child.lLocs){
							if(has.sUrl.equals(sAvail)){
								bNewLoc = false;
								break;
							}
						}
						if(bNewLoc) child.addLocation(sAvail);
					}
				}
			}
		}
		
	}
	
	@Override
	public void load(ProgressMonitor mon) throws DasResolveException {
		for(NodeDefLoc loc: lLocs){
			loc.bLoaded = false;
			loc.bBad = false;
		}
		
		for(int i = 0; i < lLocs.size(); i++){
			NodeDefLoc loc = lLocs.get(i);
			try{
				String sData = NodeFactory.getUtf8NodeDef(loc.sUrl, mon);
				JSONObject jo = new JSONObject(sData);
				
				String sVal = jo.getString(KEY_TYPE);
				
				// Using exceptions for flow control again I see.
				if(!sVal.equals( type() ))
					throw new DasResolveException("Expected type '"+TYPE+"' not '"+sVal+"'", loc.sUrl);
				
				initFromJson(jo);
				loc.bLoaded = true;
				return;
				
			} 
			catch(IOException | JSONException | ParseException | DasResolveException ex)
			{
				loc.bBad = true;
				LOGGER.log(Level.FINE, 
					"Catalog location {0} marked as bad because {1}", 
					new Object[]{loc.sUrl, ex.getMessage()}
				);
				//If this was our last chance, go ahead and raise the exception
				if((i + 1) == lLocs.size()){
					DasResolveException resEx = new DasResolveException(
						"Couldn't load collection node because "+ex.getMessage(),
						ex, loc.sUrl
					);
					throw resEx;
				}
			}
		} 
	}

	@Override
	boolean merge(ProgressMonitor mon) {
		for(NodeDefLoc loc: lLocs){
			if(loc.bLoaded || loc.bBad) continue;
			
			try{
				String sData = NodeFactory.getUtf8NodeDef(loc.sUrl, mon);
				JSONObject jo = new JSONObject(sData);
				mergeFromJson(jo);
				loc.bLoaded = true;
				return true;
				
			} catch(IOException | JSONException | ParseException ex){
				loc.bBad = true;
				LOGGER.log(Level.FINE, 
					"Catalog location {0} marked as bad because {1}", 
					new Object[]{loc.sUrl, ex.getMessage()}
				);
			}
		}
		
		return false;
	}

	@Override
	void parse(String sData, String sUrl) throws ParseException {
		JSONObject jo;
		try {
			jo = new JSONObject(sData);
			initFromJson(jo);
		} catch (JSONException ex) {
			ParseException pe = new ParseException("Error reading node data.", -1);
			pe.initCause(ex);
			throw pe;
		}
		
		// Save off the location
		for(NodeDefLoc loc: lLocs){
			if(loc.sUrl.equals(sUrl)){
				loc.bLoaded = true;
				return;
			}
		}
		
		NodeDefLoc loc = new NodeDefLoc(sUrl);
		loc.bLoaded = true;
	}
}
