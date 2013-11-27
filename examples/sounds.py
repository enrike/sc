import sc
import time, os


# we need to tell python where the scserver application lives, this
# changes between platforms and systems. You might need to edit it.
# exedir = 'usr/local' # where does scsynth live?
# sc.start(exedir, verbose=1, spew=1 )

sc.start(spew=1, verbose=1, startscsynth=0)

player2 = sc.Synth( "StereoPlayer" )
player2.bufnum = sc.loadSnd( "aaa.wav", wait=True )
print "loading bufnum with ID", player2.bufnum

time.sleep(8)

player = sc.Synth( "StereoPlayer" )
player.bufnum = sc.loadSnd( "numeros.wav", wait=True )
print "loading bufnum with ID", player.bufnum

time.sleep(8) # wait while both sounds play

sc.unloadSnd( player.bufnum )
sc.unloadSnd( player2.bufnum )

player.free()
player2.free()

sc.quit()

print 'quiting'

