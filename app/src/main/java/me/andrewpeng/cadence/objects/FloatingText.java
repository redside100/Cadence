package me.andrewpeng.cadence.objects;

public class FloatingText extends AnimatedText{

    int ticksInOneCycle, amplitude;
    int count = 0;
    int originalY;

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
