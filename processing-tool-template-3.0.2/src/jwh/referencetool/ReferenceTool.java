/**
 * you can put a one sentence description of your tool here.
 *
 * ##copyright##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 *
 * @author   ##author##
 * @modified ##date##
 * @version  ##tool.prettyVersion##
 */

package jwh.referencetool;

import processing.app.Base;
import processing.app.tools.Tool;
import processing.app.ui.Editor;

// when creating a tool, the name of the main class which implements Tool must
// be the same as the value defined for project.name in your build.properties

public class ReferenceTool implements Tool {
  Base base;
  static SplitPane newpane;


  public String getMenuTitle() {
    return "Reference Tool";
  }


  public void init(Base base) {
    // Store a reference to the Processing application itself
    this.base = base;
  }


  public void run() {
    // Get the currently active Editor to run the Tool on it
	  Editor editor = base.getActiveEditor();
	  try {
	  	if(newpane == null) {
	  		newpane = new SplitPane(editor);
	    		System.out.println("Reference Tool 1.0.0 by Jae Hyun");
	  	}
	  	newpane.pack();
	    	newpane.setVisible(true);
	  } catch (Exception e) {
	    	e.printStackTrace();
	  }
  }
}
