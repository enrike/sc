(
SynthDef(\sine, { |amp = 0.5, freq = 440|
	var data = SinOsc.ar(freq, 0, amp);
	Out.ar(0, data ! 2);
}).store;
)


(
SynthDef ( "StereoPlayer" , { 
	arg outbus=0, bufnum=0, amp=1, pan=0, mute=1, start=0, end=1, rate=1, fps=12, index=0; 
    	var phasor, length, right, left, env, clock;

    	//length = (end - start) * BufFrames.kr(bufnum); // scale from range 0 - 1 to 0 - buflength
    	//clock = LFPulse.kr(rate.abs / length, 0); // loop
    	//length = Latch.kr(length, clock); //again
	//env = EnvGen.kr( Env .new([0,1,1,0], [0.01, 0.98,0.01]), clock, timeScale:length /(rate.abs)) * amp * mute;
	phasor = Phasor.ar( 0, rate, start*BufFrames.kr(bufnum), end*BufFrames.kr(bufnum));
    	#left, right = BufRd.ar( 2, bufnum, phasor, 1 ) * amp * mute; // * env;
    	Out .ar(outbus, Balance2.ar(left, right, pan));
	
	SendTrig .kr( LFPulse.kr(fps, 0), index, phasor/BufRateScale.kr(bufnum)); //12 times per sec playhead.
}).load(s);
)


(
SynthDef ( "StereoPlayer" , { 
	arg outbus=0, bufnum=0, amp=1, pan=0, mute=1, start=0, end=1, rate=1, fps=12, index=0, pos=0; 
    	var phasor, length, right, left, env, clock;

	phasor = Phasor.ar( 
			Trig.kr(HPZ1.kr(pos).abs,0.1), 
			rate * BufRateScale.kr(bufnum), 
			start * BufFrames.kr(bufnum), 
			end * BufFrames.kr(bufnum), //
			pos * BufFrames.kr(bufnum) //
			);

    	#left, right = BufRd.ar( 2, bufnum, phasor, 1 ) * amp * mute; // * env;
    	Out.ar(outbus, Balance2.ar(left, right, pan));

	SendTrig.kr( LFPulse.kr(fps, 0), index, phasor/BufFrames.kr(bufnum) ); 
}).load(s);
)


/* Env
 (levels, times, curves, releaseNode, loopNode)
 Create a new envelope specification.

levels - an array of levels. The first level is the initial value of the envelope.

times - an array of durations of segments in seconds. There should be one fewer duration than there are levels.

curve - this parameter determines the shape of the envelope segments.
	The possible values are:
	'step' - flat segments
	'linear' - linear segments, the default
	'exponential' - natural exponential growth and decay. In this case, the levels must all be nonzero
	and the have the same sign.
	'sine' - sinusoidal S shaped segments.
	'welch' - sinusoidal segments shaped like the sides of a Welch window.
	a Float - a curvature value for all segments.
	An Array of Floats - curvature values for each segments.

releaseNode - an Integer or nil. The envelope will sustain at the release node until released.

loopNode - an Integer or nil. If not nil the output will loop through those nodes startign at the loop node to the node immediately preceeding the release node, before back to the loop node, and so on. Note that the envelope only transitions to the release node when released. Examples are below. The loop is escaped when a gate signal is sent, when the output transitions to the release node, as described below.
*/

(
SynthDef ( "StereoPlayer" , { 
	arg outbus=0, bufnum=0, amp=1, pan=0, mute=1, start=0, end=1, rate=1, fps=12, index=0, pos=0; 
    	var phasor, length, right, left, env, clock;

    	length = (end - start) * BufFrames.kr(bufnum); // scale from range 0 - 1 to 0 - buflength
    	clock = LFPulse.kr(rate.abs / length, 0); // loop
    	length = Latch.kr(length, clock); //again
	env = EnvGen.kr( Env.new([0,1,1,0], [0.01, 0.98, 0.01]), clock, timeScale:length /(rate.abs)) * amp * mute;

	phasor = Phasor.ar( 
			Trig.kr(HPZ1.kr(pos).abs,0.1), 
			rate * BufRateScale.kr(bufnum), 
			start * BufFrames.kr(bufnum), 
			end * BufFrames.kr(bufnum), //
			pos * BufFrames.kr(bufnum) //
			);

    	#left, right = BufRd.ar( 2, bufnum, phasor, 1 ) * env;
    	Out.ar(outbus, Balance2.ar(left, right, pan));

	SendTrig.kr( LFPulse.kr(fps, 0), index, phasor/BufFrames.kr(bufnum) ); 
}).load(s);
)
