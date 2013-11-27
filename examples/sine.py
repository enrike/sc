import sc
import time

print 'hello world........'

# we need to tell python where the scserver application lives, this
# changes between platforms and systems. You might need to edit it.
# exedir = 'usr/local' # where does scsynth live?
# sc.start(exedir, verbose=1, spew=1 )

sc.start( verbose=1, spew=1, startscsynth=1 )

sine = sc.Synth( "sine" )

sine.freq = 444

##sc.Synth( "sine" ).freq = 444 # <<<< in fact you could do this in this case

time.sleep(5) # stay here for 5 secs while sine plays
    
sine.free()
sc.quit()

print 'seeya world! ...........'
