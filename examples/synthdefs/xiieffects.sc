

//Distortion	
(		// mono
		SynthDef(\xiiDistortion1x1, {| inbus=0, outbus=0, pregain=0.048, postgain=15, mix = 0.5, level=0 | 
			var sig, sigtocomp, fx, y, z;
			sig = InFeedback.ar(inbus, 1);
			sigtocomp = ((sig * pregain).distort * postgain).distort;
			fx = Compander.ar(sigtocomp, sigtocomp, 1, 0, 1 );
			Out.ar(outbus, LeakDC.ar((fx * mix) + (sig *level)) );
		},[0, 0, 0.1]).load(s); 
)		
(		// stereo
		SynthDef(\xiiDistortion2x2, {| inbus=0, outbus=0, pregain=0.048, postgain=15, mix = 0.5, level=0 | 
			var sig, sigtocomp, fx, y, z;
			sig = InFeedback.ar(inbus, 2);
			sigtocomp = ((sig * pregain).distort * postgain).distort;
			fx = Compander.ar(sigtocomp, sigtocomp, 1, 0, 1 );
			Out.ar(outbus, LeakDC.ar((fx * mix) + (sig *level)) );
		},[0, 0, 0.1]).load(s); 
)



// Adcverb
(		// mono
		SynthDef(\xiiAdcverb1x1, {| inbus=0, outbus=0, revtime=3, hfdamping=0.5, mix=0.1, level=0 | 
			var fx, fxIn, sig; 
			sig = InFeedback.ar(inbus, 1); 
			fxIn = LeakDC.ar(sig) * mix;
		    	fx = AdCVerb.ar(fxIn, revtime, hfdamping, nOuts: 1);

			Out.ar(outbus, LeakDC.ar(fx + (sig * level))) // Leak DC to fix Karplus-Strong problem
		},[0.1,0.1,0.1,0.1, 0.1]).load(s); 
)		
(		// stereo
		SynthDef(\xiiAdcverb2x2, {| inbus=0, outbus=0, revtime=3, hfdamping=0.5, mix=0.1, level=0 | 
			var fx, fxIn, sig; 
			sig = InFeedback.ar(inbus, 2); 
			fxIn = LeakDC.ar(sig.sum) * mix; // make a mono in, leakdc it
		    	fx = AdCVerb.ar(fxIn, revtime, hfdamping, nOuts: 2);
			Out.ar(outbus, LeakDC.ar((sig * level) + fx)) // level
		},[0,0,0,0]).load(s); // having a lag here would result in big blow of noise at start
)


// Freeverb
(		// mono
		SynthDef(\xiiFreeverb1x1, {| inbus=0, outbus=0, mix=0.25, room=0.15, damp=0.5, fxlevel=0.75, level=0 | 
		var fx, sig; 
		sig = InFeedback.ar(inbus, 1); 
		fx = FreeVerb.ar(sig, mix, room, damp); 
		Out.ar(outbus, (fx*fxlevel) + (sig * level)) // level 
		},[0,0,0.1,0.1,0,0]).load(s); 
)
(		// stereo
		SynthDef(\xiiFreeverb2x2, {| inbus=0, outbus=0, mix=0.25, room=0.15, damp=0.5, fxlevel=0.75, level=0 | 
		var fx, sig; 
		sig = InFeedback.ar(inbus, 2); 
		fx = FreeVerb.ar(sig, mix, room, damp); 
		Out.ar(outbus, (fx*fxlevel) + (sig * level)) // level 
		},[0,0,0.1,0.1,0,0]).load(s); 
)

// Delay
(		// mono
		SynthDef(\xiiDelay1x1, {arg inbus=0,
							outbus=0, 
							maxDelay=2, // hardcoded here
							delay=0.4, 
							feedback=0.0, 
							fxlevel = 0.7, 
							level=1.0;
							
		var fx, sig; 
		sig = InFeedback.ar(inbus,1); 
		fx = sig + LocalIn.ar(1); 
		fx = DelayC.ar(fx, maxDelay, delay); 
		LocalOut.ar(fx * feedback); 
		Out.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 
)		
(		// stereo
		SynthDef(\xiiDelay2x2, {arg inbus=0,
							outbus=0, 
							maxDelay=2, // hardcoded here
							delay=0.4, 
							feedback=0.0, 
							fxlevel = 0.7, 
							level=1.0;
							
		var fx, sig; 
		sig = InFeedback.ar(inbus,2); 
		fx = sig + LocalIn.ar(2); 
		fx = DelayC.ar(fx, maxDelay, delay); 
		LocalOut.ar(fx * feedback); 
		   Out.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 
)


// Reverb
(		// mono
		SynthDef(\xiiReverb1x1, {| inbus=0,outbus=0,predelay=0.048,combdecay=15,allpassdecay=1,fxlevel=0.31,level=0 | 
			var sig, y, z;
			sig = InFeedback.ar(inbus, 1); 
			// predelay
			z = DelayN.ar(sig, 0.1, predelay);
			// 7 length modulated comb delays in parallel :
			y = Mix.ar(Array.fill(7,{ CombL.ar(z, 0.05, rrand(0.03, 0.05), combdecay) })); 
		
			6.do({ y = AllpassN.ar(y, 0.050, rrand(0.03, 0.05), allpassdecay) });
			Out.ar(outbus, (sig * level) + (y * (fxlevel*0.5))); // as fxlevel is 1 then I lower the vol a bit
		},[0,0,0.1,0.1,0,0]).load(s); 
)	
(		// stereo
		SynthDef(\xiiReverb2x2, {| inbus=0,outbus=0,predelay=0.048,combdecay=15,allpassdecay=1, fxlevel=0.31, level=0 | 
			var sig, y, z;
			sig = InFeedback.ar(inbus, 2); 
			// predelay
			z = DelayN.ar(sig, 0.1, predelay);
			// 7 length modulated comb delays in parallel :
			y = Mix.ar(Array.fill(7,{ CombL.ar(z, 0.05, rrand(0.03, 0.05), combdecay) })); 
		
			6.do({ y = AllpassN.ar(y, 0.050, rrand(0.03, 0.05), allpassdecay) });
			Out.ar(outbus, (sig*level) + (y * (fxlevel*0.5))); // as fxlevel is 1 then I lower the vol a bit
		},[0,0,0.1,0.1,0,0]).load(s); 
)

// Chorus
(		// mono
		SynthDef(\xiiChorus1x1, { arg inbus=0, outbus=0, predelay, speed, depth, ph_diff, fxlevel=0.6, level=0;
		ÊÊÊ	var in, sig, mods, numDelays = 12;
		ÊÊÊ	in = InFeedback.ar(inbus, 1);
		ÊÊÊ	mods = { |i|
		ÊÊÊÊÊÊ	SinOsc.kr(speed * rrand(0.92, 1.08), ph_diff * i, depth, predelay);
		ÊÊ	} ! numDelays;
		ÊÊÊ	sig = DelayL.ar(in, 0.5, mods);
		ÊÊÊ	sig = Mix(sig); 
			Out.ar(outbus, (sig * fxlevel) + (in * level));
		},[0, 0, 0.1]).load(s); 
)		
(		// stereo
		SynthDef(\xiiChorus2x2, { arg inbus=0, outbus=0, predelay, speed, depth, ph_diff, fxlevel=0.6, level=0;
		ÊÊÊ	var in, sig, mods, numOutChan=2, numDelays = 12;
		ÊÊÊ	in = InFeedback.ar(inbus, 2);
		ÊÊÊ	mods = { |i|
		ÊÊÊÊÊÊ	SinOsc.kr(speed * rrand(0.92, 1.08), ph_diff * i, depth, predelay);
		ÊÊ	} ! (numDelays * numOutChan);
		ÊÊÊ	sig = DelayL.ar(in, 0.5, mods);
		ÊÊÊ	sig = Mix(sig.clump(numOutChan)); 
			Out.ar(outbus, (sig * fxlevel) + (in * level));
		},[0, 0, 0.1]).load(s);
)

// Octave
(		// mono
		SynthDef(\xiiOctave1x1, {| inbus=0, outbus=0, pitch1=1, pitch2=1, vol1=0.25, vol2=0.25, dispersion=0, fxlevel=0.5, level=0 | 
		var fx1, fx2, sig; 
		sig = InFeedback.ar(inbus, 1); 
		   fx1 = PitchShift.ar(sig, 0.2, pitch1, dispersion, 0.0001);
		   fx2 = PitchShift.ar(sig, 0.2, pitch2, dispersion, 0.0001);
		Out.ar(outbus,  ( ((fx1 * vol1) + (fx2 * vol2)) * fxlevel) + (sig * level) ); 
		},[0,0,0.1,0.1,0,0]).load(s); 
)		
(		// stereo
		SynthDef(\xiiOctave2x2, {| inbus=0, outbus=0, pitch1=1, pitch2=1, vol1=0.25, vol2=0.25, dispersion=0, fxlevel=0.5, level=0 | 
		var fx1, fx2, sig; 
		sig = InFeedback.ar(inbus, 2); 
		   fx1 = PitchShift.ar(sig, 0.2, pitch1, dispersion, 0.0001);
		   fx2 = PitchShift.ar(sig, 0.2, pitch2, dispersion, 0.0001);
		Out.ar(outbus,  ( ((fx1 * vol1) + (fx2 * vol2)) * fxlevel) + (sig * level) ); 
		},[0,0,0.1,0.1,0,0]).load(s); 
)

// Tremolo
(		// mono
		SynthDef(\xiiTremolo1x1, {| inbus=0, outbus=0, freq=1, strength=1, fxlevel=0.5, level=0 | 
		var fx, sig; 
		sig = InFeedback.ar(inbus, 1); 
		fx = sig * SinOsc.ar(freq, 0, strength, 1); 
		Out.ar(outbus, (fxlevel * fx) + (sig * level)) // level 
		},[0,0,0.1,0.1,0,0]).load(s); 
)		
(		// stereo
		SynthDef(\xiiTremolo2x2, {| inbus=0, outbus=0, freq=1, strength=1, fxlevel=0.5, level=0 | 
		var fx, sig; 
		sig = InFeedback.ar(inbus, 2); 
		fx = sig * SinOsc.ar(freq, 0, strength, 1); 
		Out.ar(outbus, (fxlevel * fx) + (sig * level)) // level 
		},[0,0,0.1,0.1,0,0]).load(s); 
)

// Equialiser
(		// mono
		SynthDef(\xiiEqband1x1, { arg inbus=20, outbus=0, freq=333, rq=0.5, amp;
			var signal, in, srq;
			in = InFeedback.ar(inbus, 1);
			srq = rq.sqrt; // thanks BlackRain - Q is compensated
			signal = BPF.ar(BPF.ar(in, freq, srq), freq, srq, amp); // double BPF
			Out.ar(outbus, signal ); // 15 bands dividing 1 = 0.0666
		}).load(s);

)
(		// stereo
		SynthDef(\xiiEqband2x2, { arg inbus=20, outbus=0, freq=333, rq=0.5, amp;
			var signal, in, srq;
			in = InFeedback.ar(inbus, 2);
			srq = rq.sqrt; // thanks BlackRain - Q is compensated
			signal = BPF.ar(BPF.ar(in, freq, srq), freq, srq, amp); // double BPF
			Out.ar(outbus, signal ); // 15 bands dividing 1 = 0.0666
		}).load(s);
)

// Random Panner
(		// mono
		SynthDef(\xiiRandompanner1x2, { arg inbus=0, outbus=0, trigfreq=10, strenght=0.81;
			var sig, signal, pan, trig;
			trig = 	Dust.kr(trigfreq);
			sig =   	InFeedback.ar(inbus, 1);
			pan= 	TBetaRand.kr(-1, 1, strenght, strenght, trig);
			signal = 	Pan2.ar(sig, pan);
			Out.ar(outbus, signal);
		}).load(s);
)		
(		// stereo
		SynthDef(\xiiRandompanner2x2, { arg inbus=0, outbus=0, trigfreq=10, strenght=0.81;
			var sig, left, right, panL, panR, trig;
			trig = 	Dust.kr(trigfreq);
			sig =   	InFeedback.ar(inbus, 2);
			panL= 	TBetaRand.kr(-1, 1, strenght, strenght, trig);
			panR= 	TBetaRand.kr(-1, 1, strenght, strenght, trig);
			left =  	Pan2.ar(sig[0], panL);
			right = 	Pan2.ar(sig[1], panR);
			Out.ar(outbus, left);
			Out.ar(outbus, right);
		}).load(s);
)

// CombVocoder
(		// mono
		SynthDef(\xiiCombvocoder1x1, {arg inbus=0,
							outbus=0, 
							maxDelay=2, // hardcoded here
							delay=0.4, 
							feedback=0.0, 
							fxlevel = 0.7, 
							level=1.0;
		var fx, sig; 
		sig = InFeedback.ar(inbus,1); 
		fx = sig + LocalIn.ar(1); 
		fx = DelayC.ar(fx, maxDelay, delay); 
		LocalOut.ar(fx * feedback); 
		Out.ar(outbus, (fx * fxlevel) + (sig * level)) 
		},[0.2,0.2,0.1,0.1]).load(s); 
)
(		// stereo
		SynthDef(\xiiCombvocoder2x2, {arg inbus=0,
							outbus=0, 
							maxDelay=2, // hardcoded here
							delay=0.4, 
							feedback=0.0, 
							fxlevel = 0.7, 
							level=1.0;
		var fx, sig; 
		sig = InFeedback.ar(inbus,2); 
		fx = sig + LocalIn.ar(2); 
		fx = DelayC.ar(fx, maxDelay, delay); 
		LocalOut.ar(fx * feedback); 
		Out.ar(outbus, (fx * fxlevel) + (sig * level)) 
		},[0.2,0.2,0.1,0.1]).load(s); 
)

// MRRoque
(		// mono
		SynthDef(\xiiMrRoque1x1, {|inbus=0, outbus=0, mix = 0.25, room = 0.15, damp = 0.5, 
				outmix = 0.25, outroom = 0.15, outdamp = 0.5, 
				bufnum, rate=1, end = 4, vol=1|
			
			var in, reverb, reverb2, signal;
			
			in = InFeedback.ar(inbus, 1);
			reverb = FreeVerb.ar(in, mix, room, damp);
			BufWr.ar(reverb, bufnum, Phasor.ar(0, 1, 0, 44100*end));
			signal = BufRd.ar(1, bufnum, Phasor.ar(0, BufRateScale.kr(0) * rate, 0, 44100*end));
			reverb2 = FreeVerb.ar(signal, outmix, outroom, outdamp);	
			Out.ar(0, (signal+reverb2) * vol);
		},[0.2,0.2,0.1,0.1]).load(s); 
)				
(		// stereo
		SynthDef(\xiiMrRoque2x2, {|inbus=0, outbus=0, mix = 0.25, room = 0.15, damp = 0.5, 
				outmix = 0.25, outroom = 0.15, outdamp = 0.5, 
				bufnum, rate=1, end = 4, vol=1|
			
			var in, reverb, reverb2, signal;
			
			in = InFeedback.ar(inbus, 2);
			reverb = FreeVerb.ar(in, mix, room, damp);
			BufWr.ar(reverb, bufnum, Phasor.ar(0, 1, 0, 44100*end));
			signal = BufRd.ar(2, bufnum, Phasor.ar(0, BufRateScale.kr(0) * rate, 0, 44100*end));
			reverb2 = FreeVerb.ar(signal, outmix, outroom, outdamp);	
			Out.ar(0, (signal+reverb2) * vol);
		},[0.2,0.2,0.1,0.1]).load(s); 
)

// Multidelay
(		// mono
		SynthDef(\xiiMultidelay1x1, {arg outbus=0, inbus=0, amp = 1, bufnum,
				dtime1=1, d1amp = 0.6, 
				dtime2=3, d2amp = 0.6,
				dtime3=4, d3amp = 0.6,
				dtime4=4.5, d4amp = 0.6;
			var in, delays, d1, d2, d3, d4;
			in = InFeedback.ar(inbus, 1); 
			d1 = BufDelayN.ar(bufnum, in, dtime1) * d1amp;
			d2 = BufDelayN.ar(bufnum, in, dtime2) * d2amp;
			d3 = BufDelayN.ar(bufnum, in, dtime3) * d3amp;
			d4 = BufDelayN.ar(bufnum, in, dtime4) * d4amp;
			Out.ar(outbus, in + d1 + d2 + d3 + d4);
		}).load(s);
)
(		// stereo
		SynthDef(\xiiMultidelay2x2, {arg outbus=0, inbus=0, amp = 1, bufnum,
				dtime1=1, d1amp = 0.6, 
				dtime2=3, d2amp = 0.6,
				dtime3=4, d3amp = 0.6,
				dtime4=4.5, d4amp = 0.6;
				
			var in, delays, d1, d2, d3, d4;
			in = InFeedback.ar(inbus, 2); 
			d1 = BufDelayN.ar(bufnum, in, dtime1) * d1amp;
			d2 = BufDelayN.ar(bufnum, in, dtime2) * d2amp;
			d3 = BufDelayN.ar(bufnum, in, dtime3) * d3amp;
			d4 = BufDelayN.ar(bufnum, in, dtime4) * d4amp;
			Out.ar(outbus, in + d1 + d2 + d3 + d4);
		}).load(s);
)

// CyberPunkX
(		// mono
		SynthDef(\xiiCyberPunk1x1, {| inbus=0, outbus=0, pitchratio=0, zcperchunk=0, memlen =0, fxlevel=0.75, level=0 | 
		var fx, sig; 
		sig = InFeedback.ar(inbus, 1); 
		fx = Squiz.ar(sig, pitchratio, zcperchunk, memlen); 
		Out.ar(outbus, (fx*fxlevel) + (sig * level)) // level 
		}).load(s); 
)
(		// stereo
		SynthDef(\xiiCyberPunk2x2, {| inbus=0, outbus=0,pitchratio=0, zcperchunk=0, memlen =0,  fxlevel=0.75, level=0 | 
		var fx, sig; 
		sig = InFeedback.ar(inbus, 2); 
		fx = Squiz.ar(sig, pitchratio, zcperchunk, memlen); 
		Out.ar(outbus, (fx*fxlevel) + (sig * level)) // level 
		}).load(s); 
)
// 



