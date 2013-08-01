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
package mettel.generator.util;

/**
 * @author dmitry
 *
 */
public class MettelSignature {

	@SuppressWarnings("unused")
	private MettelSignature() {}

	private String sort;
	private String name;
	private String[] sorts;

	public MettelSignature(String sort, String name, String[] sorts){
		super();
		this.sort = sort;
		this.name = name;
		this.sorts = sorts;
	}

	public String name(){
		return name;
	}

	public String sort(){
		return sort;
	}

	public String[] arguments(){
		return sorts;
	}

	public String toString(){
		return "("+ sort+", "+name+", "+sorts+')';
	}
}
