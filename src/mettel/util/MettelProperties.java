/**
 * This file is part of MetTeL.
 *
 * MetTeL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MetTeL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MetTeL.  If not, see <http://www.gnu.org/licenses/>.
 */
package mettel.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Map.Entry;

public class MettelProperties extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5679874417325601586L;

	@Override
	public void store(Writer writer, String comments) throws IOException{
		
		TreeMap<String, String> map = new TreeMap<String, String>();
		for(Object o:keySet()){
			map.put(o.toString(), get(o).toString());
		}
		
		if(!comments.isEmpty()){
			writer.write("//"+comments);
			writer.write(MettelStrings.LINE_SEPARATOR);
		}
		
		for(Entry<String, String> e:map.entrySet()){
			writer.write(saveConvert(e.getKey(), true, false));
			writer.write(" = ");
			writer.write(saveConvert(e.getValue(), false, false));
			writer.write(MettelStrings.LINE_SEPARATOR);
		}
		writer.flush();
	}
	
    private String saveConvert(String theString,
		       boolean escapeSpace,
		       boolean escapeUnicode) {
	 int len = theString.length();
	 int bufLen = len * 2;
	 if (bufLen < 0) {
	     bufLen = Integer.MAX_VALUE;
	 }
	 StringBuffer outBuffer = new StringBuffer(bufLen);
	
	 for(int x=0; x<len; x++) {
	     char aChar = theString.charAt(x);
	     // Handle common case first, selecting largest block that
	     // avoids the specials below
	     if ((aChar > 61) && (aChar < 127)) {
	         if (aChar == '\\') {
	             outBuffer.append('\\'); outBuffer.append('\\');
	             continue;
	         }
	         outBuffer.append(aChar);
	         continue;
	     }
	     switch(aChar) {
		case ' ':
		    if (x == 0 || escapeSpace) 
			outBuffer.append('\\');
		    outBuffer.append(' ');
		    break;
	         case '\t':outBuffer.append('\\'); outBuffer.append('t');
	                   break;
	         case '\n':outBuffer.append('\\'); outBuffer.append('n');
	                   break;
	         case '\r':outBuffer.append('\\'); outBuffer.append('r');
	                   break;
	         case '\f':outBuffer.append('\\'); outBuffer.append('f');
	                   break;
	         case '=': // Fall through
	         case ':': // Fall through
	         case '#': // Fall through
	         case '!':
	             outBuffer.append('\\'); outBuffer.append(aChar);
	             break;
	         default:
	             if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode ) {
	                 outBuffer.append('\\');
	                 outBuffer.append('u');
	                 outBuffer.append(toHex((aChar >> 12) & 0xF));
	                 outBuffer.append(toHex((aChar >>  8) & 0xF));
	                 outBuffer.append(toHex((aChar >>  4) & 0xF));
	                 outBuffer.append(toHex( aChar        & 0xF));
	             } else {
	                 outBuffer.append(aChar);
	             }
	     }
	 }
	 return outBuffer.toString();
    }

    /**
     * Convert a nibble to a hex character
     * @param	nibble	the nibble to convert.
     */
    private static char toHex(int nibble) {
	return hexDigit[(nibble & 0xF)];
    }

    /** A table of hex digits */
    private static final char[] hexDigit = {
	'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };

	
}
