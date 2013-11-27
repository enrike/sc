import sc
import time, random

seed = random.Random()

# we need to tell python where the scserver application lives, this
# changes between platforms and systems. You might need to edit it.
# exedir = 'usr/local' # where does scsynth live?
# sc.start( exedir,  verbose=0, spew=0 )

sc.start()

mygroup = sc.Group( )
print 'created new group with ID ', mygroup.groupID

playerlist = []

for s in xrange(3) :
    player = sc.Synth( "StereoPlayer", addTargetID=mygroup.groupID )
##    outbus=0, bufnum=0, amp=1, pan=0, mute=1, start=0, end=1, rate=1, fps=12, index=0;
    player.bufnum = 0 # need to declare here the vars we will be manipulating later
    player.amp = 1
    player.pan = 0
    player.rate = 1
    playerlist.append(player) ## 

mygroup.bufnum = sc.loadSnd( "numeros.wav" ) ## all share same sound bufnum
mygroup.outbus = 0 # all use same bus

print "loading bufnum with ID", player.bufnum

timeout =  time.time()  + 10 # secs

while time.time() < timeout :
    ## all share same pan and amp
    mygroup.pan = (seed.random()*2)-1 # -1 to 1
    mygroup.amp = seed.random()
    print 'pan', mygroup.pan, 'amp', mygroup.amp
    # but each has a different rate
    for player in playerlist :
        player.rate = (seed.random() - 0.5) * 6 # range float -3 to 3
        
    time.sleep(2) # every two secs
##    print "timeout in %f secs" % (timeout - time.time() + 2) 


sc.unloadSnd( player.bufnum )
for p in playerlist : p.free() ## free all
sc.quit()
print 'quiting'
