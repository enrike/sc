import sc
import time, random

seed = random.Random()



# we need to tell python where the scserver application lives, this
# changes between platforms and systems. You might need to edit it.
# exedir = 'C:\Program Files\PsyCollider\PsyCollider'
# sc.start( exedir, verbose=1, spew=1 )

sc.start( verbose=1, spew=1 )

sine = sc.Synth( "sine" )
sine.freq = 444

timeout = time.time() + 10 # 10 secs

while time.time() < timeout :
    time.sleep(0.5) # loop every half sec
    sine.amp = seed.random() # 0 to 1
    sine.freq = seed.randint(300, 1500) 

    
sine.free()
sc.quit()

print "quiting"
