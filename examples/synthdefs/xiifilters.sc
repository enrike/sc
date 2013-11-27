// bandpass
(		// mono
		SynthDef(\xiiBPF1x1, {arg inbus=0,
							outbus=0, 
							freq=200,
							rq=0.4, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus,1); 
		Ê Êfx = BPF.ar(sig, freq, rq); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)
(		// stereo
		SynthDef(\xiiBPF2x2, {arg inbus=0,
							outbus=0, 
							freq=200,
							rq=0.4, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus, 2); 
		Ê Êfx = BPF.ar(sig, freq, rq); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)


// Lowpass
(		// mono
		SynthDef(\xiiLPF1x1, {arg inbus=0,
							outbus=0, 
							freq=200, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus,1); 
		Ê Êfx = LPF.ar(sig, freq); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)
(		// stereo
		SynthDef(\xiiLPF2x2, {arg inbus=0,
							outbus=0, 
							freq=200,
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus, 2); 
		Ê Êfx = LPF.ar(sig, freq); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)


// highpass
(		// mono
		SynthDef(\xiiHPF1x1, {arg inbus=0,
							outbus=0, 
							freq=200,
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus,1); 
		Ê Êfx = HPF.ar(sig, freq); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)
(		// stereo
		SynthDef(\xiiHPF2x2, {arg inbus=0,
							outbus=0, 
							freq=200,
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus, 2); 
		Ê Êfx = HPF.ar(sig, freq); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 
)


// RLowPass
(		// mono
		SynthDef(\xiiRLPF1x1, {arg inbus=0,
							outbus=0, 
							freq=200,
							rq=0.4, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus,1); 
		Ê Êfx = RLPF.ar(sig, freq, rq); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)
(		// stereo
		SynthDef(\xiiRLPF2x2, {arg inbus=0,
							outbus=0, 
							freq=200,
							rq=0.4, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus, 2); 
		Ê Êfx = RLPF.ar(sig, freq, rq); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 
)



// RHighPass
(		// mono
		SynthDef(\xiiRHPF1x1, {arg inbus=0,
							outbus=0, 
							freq=200,
							rq=0.4, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus,1); 
		Ê Êfx = RHPF.ar(sig, freq, rq); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)
(		// stereo
		SynthDef(\xiiRHPF2x2, {arg inbus=0,
							outbus=0, 
							freq=200,
							rq=0.4, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus, 2); 
		Ê Êfx = RHPF.ar(sig, freq, rq); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)



// Resonant
(		// mono
		SynthDef(\xiiResonant1x1, {arg inbus=0,
							outbus=0, 
							freq=200,
							rq=0.4, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus,1); 
		Ê Êfx = Resonz.ar(sig, freq, rq); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)
(		// stereo
		SynthDef(\xiiResonant2x2, {arg inbus=0,
							outbus=0, 
							freq=200,
							rq=0.4, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus, 2); 
		Ê Êfx = Resonz.ar(sig, freq, rq); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 
)


// Klanks
(		// mono
		SynthDef(\xiiKlanks1x1, {arg inbus=0,
							outbus=0, gain=0.01,
							freq1, freq2, freq3, freq4,
							amp1, amp2, amp3, amp4,
							ring1, ring2, ring3, ring4,
							fxlevel = 0.7, 
							level=0;
							
		Ê Êvar fx1, fx2, fx3, fx4, sig; 
		Ê Êsig = InFeedback.ar(inbus, 1); 
		Ê Êfx1 = Ringz.ar(sig*gain, freq1, ring1, amp1); 
		Ê Êfx2 = Ringz.ar(sig*gain, freq2, ring2, amp2); 
		Ê Êfx3 = Ringz.ar(sig*gain, freq3, ring3, amp3); 
		Ê Êfx4 = Ringz.ar(sig*gain, freq4, ring4, amp4); 
		Ê ÊOut.ar(outbus, ((fx1+fx2+fx3+fx4) *fxlevel) + (sig * level)) 
		}).load(s); 	
)
(		// stereo
		SynthDef(\xiiKlanks2x2, {arg inbus=0,
							outbus=0, gain=0.01,
							freq1, freq2, freq3, freq4,
							amp1, amp2, amp3, amp4,
							ring1, ring2, ring3, ring4,
							fxlevel = 0.7, 
							level=0;
							
		Ê Êvar fx1, fx2, fx3, fx4, sig; 
		Ê Êsig = InFeedback.ar(inbus, 2); 
		Ê Êfx1 = Ringz.ar(sig*gain, freq1, ring1, amp1); 
		Ê Êfx2 = Ringz.ar(sig*gain, freq2, ring2, amp2); 
		Ê Êfx3 = Ringz.ar(sig*gain, freq3, ring3, amp3); 
		Ê Êfx4 = Ringz.ar(sig*gain, freq4, ring4, amp4); 
		Ê ÊOut.ar(outbus, ((fx1+fx2+fx3+fx4) *fxlevel) + (sig * level)) 
		}).load(s); 
)


// MoogVCFFF
(		// mono
		SynthDef(\xiiMoogVCFFF1x1, {arg inbus=0,
							outbus=0, 
							freq=200,
							gain=1, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus,1); 
		Ê Êfx = MoogFF.ar(sig, freq, gain); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)
(
		// stereo
		SynthDef(\xiiMoogVCFFF2x2, {arg inbus=0,
							outbus=0, 
							freq=200,
							gain=1, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus, 2); 
		Ê Êfx = MoogFF.ar(sig, freq, gain); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)


// MoogVCF
(		// mono
		SynthDef(\xiiMoogVCF1x1, {arg inbus=0,
							outbus=0, 
							freq=200,
							gain=1, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus,1); 
		Ê Êfx = MoogVCF.ar(sig, freq, gain); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)
(		// stereo
		SynthDef(\xiiMoogVCF2x2, {arg inbus=0,
							outbus=0, 
							freq=200,
							gain=1, 
							fxlevel = 0.7, 
							level=1.0;
							
		Ê Êvar fx, sig; 
		Ê Êsig = InFeedback.ar(inbus, 2); 
		Ê Êfx = MoogVCF.ar(sig, freq, gain); 
		Ê ÊOut.ar(outbus, (fx * fxlevel) + (sig * level)) 
		}).load(s); 	
)


// 
