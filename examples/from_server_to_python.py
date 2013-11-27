import sc
import time, os

""" just prints the value sent by scserver, in this case the playhead position in the sound
    being 0 the begining and 1 the end of the sample
    This is only because the StereoPlayer synthdef has coded a trigger object that sends periodically
    the playhead location. 
"""

# we need to tell python where the scserver application lives, this
# changes between platforms and systems. You might need to edit it.
#exedir = 'usr/local' # where does scsynth live?
# sc.start(exedir, verbose=1, spew=1 )

sc.start( spew=1 )

def dothis(msg) :
    print 'play head at ', msg[3]

sc.register( '/tr', dothis ) # call dothis function when a /tr message arrives

player = sc.Synth( "StereoPlayer" )

player.bufnum = sc.loadSnd( "numeros.wav", wait=True )

print "loading bufnum with ID", player.bufnum

time.sleep(5) # wait while sound plays

sc.unloadSnd( player.bufnum )
player.free()
sc.quit()

print 'quiting'

