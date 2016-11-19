    SC 0.3.1 ----> Control supercollider sound server from Python
    ixi software - Jan 2012
    www.ixi-software.net | www.ixi-audio.net   


License : GNU GPL

This application is free software; you can redistribute it and/or modify it under the terms of the GNU, General Public License as 
published by the Free Software Foundation.

This aplication is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this library; if not, write to the Free Software 
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

scsynth / scosc libraries by Patrick Stinson are under GNU GPL .



Description:
SC allows to easily control Supercollider (http://en.wikipedia.org/wiki/SuperCollider) sound server (scsynth) from Python.
It wraps scsynth / scosc libraries by Patrick Stinson (http://trac2.assembla.com/pkaudio). They allow Python to talk to scsynth
via OpenSoundControl (http://en.wikipedia.org/wiki/Open_Sound_Control). SC provides with a simpler API 
to use those libraries. However advance users might want to bypass SC and use those libraries directly.

Using SC, Supercollider Synthdefs can be loaded into the scsynth server therefore can be totally controlled from python.
New synthdefs can be instantiated in real time and its sound conections can be changed. SC can also be used from 
interactive Python to do live coding (http://en.wikipedia.org/wiki/Live_coding).

For an advanced real life example check Slicer this application uses Python+Opengl+WxPython to create a GUI that controls a scsynth sound engine process that runs in the background.
http://ixi-audio.net/content/body_software_slicer.html


Installing :
SC, like any other python library, can be installed in your system Python folder or it  can just live next to the files that 
import it. To install run from the terminal 
$ python setup.py install



Usage :
Check examples provided at SC folder. For a real life complex example check Slicer app at www.ixi-audio.net

Supercollider language (sclang) allows to compile crossplatform .scsyndef files. This synthdefs describe a set of sound conection between 
Ugens or Unit Generators. They can be loaded into the scserver and controlled. SC allows to easily wrap around those synthdefs. 
For example a simple synthdef that sends a sine wave to the sound card would look like this in sclang :

(
SynthDef(\sine, { |amp = 0.5, freq = 440|
	var data = SinOsc.ar(freq, 0, amp);
	Out.ar(0, data ! 2);
}).store;
)

Compiling this code would produce a file called sine.scsyndef (in the supercollider synthdefs folder) that can be later loaded 
into scsynth and controlled by SC, like this:

sine = sc.Synth( "sine", args=["freq", 600, "amp", 0.5] )
sine.freq = 444
sine.amp = 0.9
... and so on ...

sc.Synth class provides a class that can dynamically adds new variables when needed, this way we dont need to subclass sc.Synth unless
we need some kind of calculation with the variables. if we just set or get them we cn just do it directly. Ideally you are able to 'mirror' each 
of the synthdefs argument with a variable in the sc.Synth class like in the example above. Check the sine.py and sounds.py examples for
more details.

NOTE that stereo synthdefs use TWO chanels, so if you say out bus is number 2. it will take 2 and 3 to
play the stereo. Be careful not to use chanels that are already taken by some other synthdef
0 and 1 are the default sound out chanel. So setting a synthdef out to 0 means directing its sound
output to the sound card . Sound in from sound card should be 5 and 6. Make sure you use stereo samples
with stereo synthdefs. Otherwise they might not work.

In some systems (specially windows) you must point to the right location of the scsynth, this changes between systems 
(linux, OSX, windows). This is done when starting the module. On linux this is usually either /usr/bin or /user/local/bin, On Windows usually C:\Program Files\Psycollider and on 
Mac /Applications/Supercollider. Find where the file is in your system and point to it this way :
sc.start( exedir='C:\Program Files\PsyCollider' )

API: there is an API description few lines below. Check  sc/sc.py file code for in depth documentation of classes and functions.




Synthdefs :
There are few synthdefs included in the synthdefs folder ready for use. The source code is included as well. We hope they are a nice way
to get into Supercollider programming.



Requirements:
scsynth. This sould be in your system if you have Supercollider installed. Usually you need to point to the directory where 
scsynth file is. 



Bugs / to do :



Versions:
0.3.1
- removed -b option. updated to latest version of server.py. this solves problem with buffer ids

0.3 
- added more scsynth startup options to process start
- detach scsynth startup from library start

0.2
- few changes in API to allow for manual controls of scsynth
- included scsynth and scosc libraries

0.1
- basic functionality created
- comiled scsyndefs and source added



To do:
- quit scsynth process when quiting (if controlled from python)
- make sure verbose is propagated all the way
- use server.receive('/fail', '/loaded') when loading samples and synthdefs instead of just time.sleep()




- API description ---------------------------------

# Variables #

sndpath
	directory where to look for files when loading sounds

	sc.sc.sndpath = 'C:\mysounds'

synthdefpath
	directory where to look for files when loading synthdefs 
	
	sc.sc.synthdefpath = '/home/myuser/Desktop/mysynthdefs'


# Functions #
def start( exedir='', port=57110, inputs=2, outputs=2, samplerate=44100, verbose=0,
           spew=0, startscsynth=1 ) :
	Starts the scsynth server process in the background with given arguments.
	exedir : directory where scsynth lives
	port : supercollider port. you dont need to change this usually
	inputs : number of sound inputs
	outputs : number of sound outputs 
	samplerate : sample rate for server
	verbose : 1 or 0, it will print in the python console answers from sever. For debugging
	spew : 1 or 0, it will print in the python console messages sent from python to the server. For debugging
	startscsynth: 1 or 0, starts scsynth process or not 

	sc.start(exedir='C:\Program Files\PsyCollider', samplerate=48000, spew=1)
	sc.start(exedir=os.getcwd(),  spew=1, verbose=1)

quit()
	quits scsynth process

	sc.quit()

register( address, function )  
	binds an OSC address and a function. To manipulate messages from server.
	
	def printAll(self, msg) : print 'OSC msg received >> ', msg
	sc.register( '/b_info', printAll ) 
	
loadSnd( filename, wait )  -> buffnum
	loads sound file into servre from current sound folder and retuns integer buffnum ID
	filename : name of sound file
	wait : True/False. Wait for sound to load. Default is False
	
	sc.loadSnd( 'numeros.wav' )

loadSndAbs(path, wait)  -> buffnum
	loads sound file into server from absolute path and retuns integer buffnum ID
	path : absolute path to sound file
	wait : True/False. Wait for sound to load. Default is False
	
	sc.loadSndAbs( '/home/myuser/sounds/numeros.wav' )

unloadSnd( buf_id ) 
	Unloads a sound from the server memory
	
	buffnumID = sc.loadSnd( 'numeros.wav' )
	sc.unloadSnd( buffnumID )



# Classes #

Synth( stringdefname, nodeID, addAction, addTargetID ) -> synthdef instance
	Loads and instantiates the given synthdef . Returns the synthdef instance
	You candefine more properties for instances of this class by intialisating them directly. You dont need to extend the class for this.
	check sine.py example and sc.py for more documentation

	delay = sc.Synth( 'XiiDelay' )
	delay.fxlevel = 1
	delay.free()

Group( groupID, addAction, addTargetID ) -> group instance
	Controls synths that are asigned to this group. Returns the group instance.
	check groups.py example and sc.py for more documentation
	
	sines = sc.Group()
	print sines.groupID
	synth1 = sc.Synth('sine',addTargetID=sines.groupID )
	synth2 = sc.Synth('sine',addTargetID=sines.groupID )
	synth1.freq = 333
	synth2.freq = synth1.freq * 2.5
	sines.amp = 1 # controls both at once


----------------------------------------



thanks to patrick stinson for making possible to control supercollider from python with scosc/scsynth libraries,

