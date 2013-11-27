import sc, time, os


sc.start(verbose=1)

time.sleep(3) # wait while sound plays

s = sc.sc.server ###################

s.sendMsg('/d_load', os.path.join(os.getcwd(),'synthdefs','StereoPlayer.scsyndef'));

time.sleep(1)

# no data at buffer index 500 -- silent synth
s.sendMsg("/s_new", "StereoPlayer", 1967)

time.sleep(1)

s.sendMsg('/b_allocRead', 1000, os.path.join(os.getcwd(),'sounds','numeros.wav'), 0, -1);

time.sleep(1) 

# but here, of course
s.sendMsg("/n_set", 1967, "bufnum", 1000);
s.sendMsg("/n_set", 1967, "pos", 0.1);


s.sendMsg('/d_load', os.path.join(os.getcwd(),'synthdefs','sine.scsyndef'));

time.sleep(1)

s.sendMsg("/s_new", "sine", 1968);

time.sleep(3)

s.sendMsg("/quit")
