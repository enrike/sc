import sc
import time, random

seed = random.Random()

# we need to tell python where the scserver application lives, this
# changes between platforms and systems. You might need to edit it.
# exedir = 'usr/local' # where does scsynth live?
# sc.start(exedir, verbose=1, spew=1 )

sc.start()

player = sc.Synth( "StereoPlayer" )
## outbus=0, bufnum=0, amp=1, pan=0, mute=1, start=0, end=1, rate=1, fps=12, index=0;

player.bufnum = sc.loadSnd( "numeros.wav" )

print "loading bufnum with ID", player.bufnum

timeout =  time.time()  + 10 # secs

while time.time() < timeout :
    player.pan = seed.random()
    player.amp = seed.random()
    player.rate = (seed.random() - 0.5) * 6 # range float -3 to 3
    time.sleep(2) # every two secs
    print "timeout in %f secs" % (timeout - time.time()) 


sc.unloadSnd( player.bufnum )
player.free() 
sc.quit()
print 'quiting'
