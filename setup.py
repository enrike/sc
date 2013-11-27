#!/usr/bin/env python

# please report errors to info@ixi-audio.net
# to install :
# python setup.py install

# create a distribution :
# python setup.py sdist

#create win exe installer :
# python setup.py bdist_wininst




from distutils.core import setup



setup(name = 'sc',
    version = '0.2',
    description = 'Control SuperCollider sound server from Python',
    license = 'GPL',
    author = 'ixi audio',
    author_email = 'info@ixi-audio.net',
    url = 'http://www.ixi-audio.net/backyard',

    packages = [ 'sc', 'scsynth', 'scosc' ],
    data_files = [ ]
)




