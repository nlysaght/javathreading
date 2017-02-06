/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadsample;

/**
 *
 * @author lysag
 */
public interface IStringWalker {
    boolean MoveNext();
    boolean CanMove();
    char getCurrentCharacter();
    int getCurrentPosition();
    void Reset();
}
