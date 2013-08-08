/**
 * MetTeL is a tableau prover generator.
 * Copyright (C) 2009-2011 Dmitry Tishkovsky
 *
 * This file is part of MetTeL.
 *
 * MetTeL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version with the additional term
 * that all the references to the MetTeL original author,
 * Dmitry Tishkovsky, must be retained.
 *
 * MetTeL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MetTeL.  If not, see <http://www.gnu.org/licenses/>.
 */
package mettel.generator;

import java.util.ArrayList;

import mettel.MettelRuntimeException;

/**
 * @author dmitry
 *
 */
public class MettelEqualityKeywords implements MettelEqualityDefaultKeywords{


	private final String[] keywords;
	/**
	 *
	 */
	public MettelEqualityKeywords() {
		this(EQUALITY_KEYWORDS);
	}
	
	public MettelEqualityKeywords(MettelEqualityKeywords kw) {
		this(kw.keywords);
	}

	public MettelEqualityKeywords(String[] keywords) {
		super();
		if(keywords == null) keywords = EQUALITY_KEYWORDS.keywords;
		final int SIZE = keywords.length; 
		this.keywords = new String[SIZE];
		System.arraycopy(keywords, 0, this.keywords, 0, SIZE);
	}

	public MettelEqualityKeywords(String keywordString) {
		this(parseKeywords(keywordString));
	}

	public static String[] parseKeywords(String keywordString){

		final int LENGTHM1 = keywordString.length() - 1;
    	if(keywordString.charAt(0) != '{' || keywordString.charAt(LENGTHM1) != '}')
    		throw new MettelRuntimeException("Wrong equality keywords format in \""+keywordString+'\"');

    	ArrayList<String> result = new ArrayList<String>();
    	int i0 = 1, i1 = keywordString.indexOf(',', i0 + 1);
    	while(i1 != -1){
    		result.add(keywordString.substring(i0, i1).trim());
    		i0 = i1 + 1;
    		i1 = keywordString.indexOf(',', i0);
    	}
    	result.add(keywordString.substring(i0, LENGTHM1).trim());

    	//System.out.println("###############################"+result);

    	return result.toArray(new String[result.size()]);
	}

	public boolean isEquality(String id){
		for(String s:keywords){
			if(s.equals(id)) return true;
		}
		return false;
	}

	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append('{');
		int i = 0;
		for(String s:keywords){
			if(i > 0) b.append(',');
			b.append(s);
			i++;
		}
		b.append('}');
		return b.toString();
	}
}
