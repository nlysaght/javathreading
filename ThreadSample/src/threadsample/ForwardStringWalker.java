package threadsample;

public class ForwardStringWalker implements IStringWalker {
    // Internal string buffer that we will walk.
    private String buffer;
    // Current position we are at in the string. -1 Represents the start of the string.
    private int currentPosition = -1;
    public ForwardStringWalker(String buffer)
    {
        this.buffer = buffer;
        Reset();
    }

    
    // Move onto the next position in the string buffer.
    @Override
    public boolean MoveNext() {
        if(this.currentPosition < this.buffer.length()-1)
        {
            this.currentPosition++;
            return true;
        }
        return false;
    }

    // Returns the current character at it's position in the string.
    @Override
    public char getCurrentCharacter() {
        return buffer.charAt(this.currentPosition);
    }

    // Returns the current position we are at in the string.
    @Override
    public int getCurrentPosition() {
        return this.currentPosition;
    }

    // Reset the position. Back to the start ofr a forward walker.
    @Override
    public void Reset() {
        currentPosition = -1;
    }
    @Override
    public boolean CanMove() {
        return this.currentPosition < this.buffer.length() -1;
    }

}
