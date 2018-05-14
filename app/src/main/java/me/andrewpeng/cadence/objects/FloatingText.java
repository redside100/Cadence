package me.andrewpeng.cadence.objects;

public class FloatingText extends AnimatedText{

    int ticksInOneCycle, amplitude;
    int count = 0;
    int originalY;

    /**
     * Creates a line of floating text that will move up and down in a continuous cycle
     * @param text Text that will be floating
     * @param x x location
     * @param y y location
     * @param textSize Text Size
     * @param color Color of the text
     * @param ticksInOneCycle How long it takes for the floating text to complete a single cycle
     * @param amplitude The max/min height that the floating text will reach
     * @param alpha The transparency of the text
     */
    public FloatingText(String text, int x, int y, int textSize, int color, int ticksInOneCycle, int amplitude, int alpha){
        super(text, x, y, textSize, color, alpha);
        originalY = y;
        this.ticksInOneCycle = ticksInOneCycle;
        this.amplitude = amplitude;
    }

    @Override
    public void tick(){
        super.tick();
        double pi = Math.PI;
        double k = (2 * pi) / ticksInOneCycle;
        double currentOffset = amplitude * Math.sin(k * count);
        y = (int) (originalY - currentOffset);
        count++;
        if (count == ticksInOneCycle)
            count = 0;
    }

}
