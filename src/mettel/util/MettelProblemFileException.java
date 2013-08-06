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

/**
 * @author Dmitry Tishkovsky
 * @version $Revision$ $Date$
 *
 */
public class MettelProblemFileException extends RuntimeException{


	protected Exception cause = null;

	public MettelProblemFileException(){
		this(null, null);
	}

	public MettelProblemFileException(Exception cause, String description){
		super(description);
		this.cause = cause;
	}

	public MettelProblemFileException(Exception cause){
		this(cause, null);
	}

	public MettelProblemFileException(String description){
		this(null,description);
	}

	public String getLocalizedMessage(){

	    String result = super.getLocalizedMessage();
	    if(result==null) result = getClass().getName();
	    if(cause != null){
		    String causeDescription = getLocalizedMessage();
		    result+="\n CAUSED BY \n" +
			(causeDescription==null? "(NO DESCRIPTION)" : causeDescription);
	    }

	    return result;

	}

}
