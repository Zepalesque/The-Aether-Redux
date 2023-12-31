package net.zepalesque.redux.util.math;


import net.minecraft.util.Mth;

/**
 *  Ported to Java from C#, based on <a href="https://gist.github.com/Fonserbc/3d31a25e87fdaa541ddf">...</a>
 */
public class EasingUtil {

    public static float Linear (float k) {
        return k;
    }

    public static class Quadratic
    {
        public static float in (float k) {
            return k*k;
        }

        public static float out (float k) {
            return k*(2f - k);
        }

        public static float inOut (float k) {
            if ((k *= 2f) < 1f) return 0.5f*k*k;
            return -0.5f*((k -= 1f)*(k - 2f) - 1f);
        }

        public static float Bezier (float k, float c) {
            return c*2*k*(1 - k) + k*k;
        }
    }

    public static class Cubic
    {
        public static float in (float k) {
            return k*k*k;
        }

        public static float out (float k) {
            return 1f + ((k -= 1f)*k*k);
        }

        public static float inOut (float k) {
            if ((k *= 2f) < 1f) return 0.5f*k*k*k;
            return 0.5f*((k -= 2f)*k*k + 2f);
        }
    }

    public static class Quartic
    {
        public static float in (float k) {
            return k*k*k*k;
        }

        public static float out (float k) {
            return 1f - ((k -= 1f)*k*k*k);
        }

        public static float inOut (float k) {
            if ((k *= 2f) < 1f) return 0.5f*k*k*k*k;
            return -0.5f*((k -= 2f)*k*k*k - 2f);
        }
    }

    public static class Quintic
    {
        public static float in (float k) {
            return k*k*k*k*k;
        }

        public static float out (float k) {
            return 1f + ((k -= 1f)*k*k*k*k);
        }

        public static float inOut (float k) {
            if ((k *= 2f) < 1f) return 0.5f*k*k*k*k*k;
            return 0.5f*((k -= 2f)*k*k*k*k + 2f);
        }
    }

    public static class Sinusoidal
    {
        public static float in (float k) {
            return 1f - Mth.cos((float) (k*Math.PI/2f));
        }

        public static float out (float k) {
            return Mth.sin((float) (k*Math.PI/2f));
        }

        public static float inOut (float k) {
            return 0.5f*(1f - Mth.cos((float) (Math.PI*k)));
        }
    }

    public static class Exponential
    {
        public static float in (float k) {
            return k == 0f? 0f : (float) Math.pow(1024f, k - 1f);
        }

        public static float out (float k) {
            return k == 1f? 1f : (float) (1f - Math.pow(2f, -10f * k));
        }

        public static float inOut (float k) {
            if (k == 0f) return 0f;
            if (k == 1f) return 1f;
            if ((k *= 2f) < 1f) return (float) (0.5f*Math.pow(1024f, k - 1f));
            return (float) (0.5f*(-Math.pow(2f, -10f*(k - 1f)) + 2f));
        }
    }

    public static class Circular
    {
        public static float in (float k) {
            return (float) (1f - Math.sqrt(1f - k*k));
        }

        public static float out (float k) {
            return (float) Math.sqrt(1f - ((k -= 1f)*k));
        }

        public static float inOut (float k) {
            if ((k *= 2f) < 1f) return (float) (-0.5f*(Math.sqrt(1f - k*k) - 1));
            return (float) (0.5f*(Math.sqrt(1f - (k -= 2f)*k) + 1f));
        }
    }

    public static class Elastic
    {
        public static float in (float k) {
            if (k == 0) return 0;
            if (k == 1) return 1;
            return (float) (-Math.pow( 2f, 10f*(k -= 1f))* Mth.sin((float) ((k - 0.1f)*(2f*Math.PI)/0.4f)));
        }

        public static float out (float k) {
            if (k == 0) return 0;
            if (k == 1) return 1;
            return (float) (Math.pow(2f, -10f*k)*Mth.sin((float) ((k - 0.1f)*(2f*Math.PI)/0.4f)) + 1f);
        }

        public static float inOut (float k) {
            if ((k *= 2f) < 1f) return (float) (-0.5f*Math.pow(2f, 10f*(k -= 1f))*Mth.sin((float) ((k - 0.1f)*(2f*Math.PI)/0.4f)));
            return (float) (Math.pow(2f, -10f*(k -= 1f))*Mth.sin((float) ((k - 0.1f)*(2f*Math.PI)/0.4f))*0.5f + 1f);
        }
    }

    public static class Back
    {
        static float s = 1.70158f;
        static float s2 = 2.5949095f;

        public static float in (float k) {
            return k*k*((s + 1f)*k - s);
        }

        public static float out (float k) {
            return (k -= 1f)*k*((s + 1f)*k + s) + 1f;
        }

        public static float inOut (float k) {
            if ((k *= 2f) < 1f) return 0.5f*(k*k*((s2 + 1f)*k - s2));
            return 0.5f*((k -= 2f)*k*((s2 + 1f)*k + s2) + 2f);
        }
    }

    public static class Bounce
    {
        public static float in (float k) {
            return 1f - out(1f - k);
        }

        public static float out (float k) {
            if (k < (1f/2.75f)) {
                return 7.5625f*k*k;
            }
            else if (k < (2f/2.75f)) {
                return 7.5625f*(k -= (1.5f/2.75f))*k + 0.75f;
            }
            else if (k < (2.5f/2.75f)) {
                return 7.5625f *(k -= (2.25f/2.75f))*k + 0.9375f;
            }
            else {
                return 7.5625f*(k -= (2.625f/2.75f))*k + 0.984375f;
            }
        }

        public static float inOut (float k) {
            if (k < 0.5f) return in(k*2f)*0.5f;
            return out(k*2f - 1f)*0.5f + 0.5f;
        }
    }
}
