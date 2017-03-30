package com.github.sufbo.entities.visitor;

import com.github.sufbo.entities.java.Class;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.Classes;
import com.github.sufbo.entities.maven.Artifact;
import com.github.sufbo.entities.maven.Artifacts;
import com.github.sufbo.entities.maven.Ids;

/**
 * <ul>
 *   <li><code>visit(Artifacts)</code>
 *     <ul>
 *       <li><code>visit(Artifact)*</code>
 *         <ul>
 *           <li><code>visit(Ids, Classes)*</code>
 *             <ul>
 *               <li><code>visit(GroupId, ArtifactId, Version)</code></li>
 *               <li><code>visit(Class)*</code>
 *                 <ul>
 *                   <li><code>visit(ClassName, MethodInformation, Bytecode)*</code></li>
 *                 </ul>
 *               </li>
 *             </ul>
 *           </li>
 *           <li><code>visitFailed(Ids, Exception)*</code></li>
 *         </ul>
 *       </li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * @author Haruaki Tamada
 */
public interface ArtifactVisitor extends Visitor {
    default void visit(Artifacts artifacts){
        artifacts.accept(this);
    }

    default void visit(Artifact artifact){
        artifact.accept(this);
    }

    default void visit(Ids ids, Classes classes){
        ids.accept(this);
        classes.accept(this);
    }

    /**
     * This method is called when artifacts building phase was failed (failed on reading jar file).
     * @param ids groupId, artifactId, version.
     * @param e exception.
     */
    default void visitFailed(Ids ids, Exception e){
    }

    /**
     * This method is called when parsing of class file was failed (illegal class format).
     * 
     * @param name class name.
     * @param e exception.
     */
    default void visitFailed(ClassName name, Exception e){
    }

    default void visit(Class target){
        target.accept(this);
    }
}
