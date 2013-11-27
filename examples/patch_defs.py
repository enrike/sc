import sc
import time, os

"""
Parameters from synthdefs used below that can be controlled :

- Delay :
inbus=0, outbus=0, delay=0.4, feedback=0.0, fxlevel = 0.7, level=1.0;
- Octave :
inbus=0, outbus=0, pitch1=1, pitch2=1, vol1=0.25, vol2=0.25, dispersion=0, fxlevel=0.5, level=0
- StereoPlayer :
outbus=0, bufnum=0, amp=1, pan=0, mute=1, start=0, end=1, rate=1, fps=12, index=0

note that we just declare the ones that we need to set or manipulate. the rest will take the default
values listed above.

 NOTE that stereo synthdefs take TWO chanels, so if you set out to 2. it will take 2 and 3 to
 play the stereo. Be careful not to use chanels that are already taken by some other synthdef
 0 and 1 are the default sound out chanels, setting a synthdef out to 0 means directing its sound
 output to the sound card . Sound in from sounds card should be 5 and 6
"""

# we need to tell python where the scserver application lives, this
# changes between platforms and systems. You might need to edit it.
# exedir = '/usr/bin' # where does scsynth live?
# sc.start( exedir, verbose=1, spew=0 )

sc.start()

# sampler player
player = sc.Synth( "StereoPlayer" )
player.bufnum = sc.loadSnd( "numeros.wav", wait=True )
player.rate = 0 # stopped now
print "loading bufnum with ID", player.bufnum

# delay
delay = sc.Synth( "xiiDelay2x2" )
delay.feedback = 0 # init paramenters that we will manipulate in this script
delay.fxlevel = 0

# octave
octave = sc.Synth( "xiiOctave2x2" )
octave.pitch1 = 0.1
octave.pitch2 = 1
octave.fxlevel = 1 # processed sound
octave.level = 0 # clean sound

# player > delay > octave > snd card
player.outbus = delay.inbus = 4
delay.outbus = octave.inbus = 6
# octave.outbus = 0 # sound card. It is 0 by default so we dont need to do this one.

player.rate = 1 # play it now

timeout =  time.time()  + 10 # secs
while time.time() < timeout :
    time.sleep(0.1) # every tenth of sec
    delay.feedback += 0.01 # more feedback
    delay.fxlevel +=  0.01 # higher delayed sound level
    octave.pitch1 += 0.01 # increase
    octave.pitch2 -= 0.01 # decrease
    print 'fxlevel: %f / feedback: %f / octave pitch1: %f and pitch2: %f : ' % ( delay.fxlevel, delay.feedback, octave.pitch1, octave.pitch2 ) 

sc.unloadSnd( player.bufnum )
player.free()
sc.quit()
print 'quiting'




