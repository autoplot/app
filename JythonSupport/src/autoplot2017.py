# autoplot2017.py v2.00 # do not edit this file, changes will be lost.  
# See "2.00" below, and in org.autoplot.jythonsupport.JythonUtil
# This version appears in JythonUtil as well!
# This is copied into each enduser's autoplot_data/jython folder to be picked up 
# along with all the other python codes.

from org.das2.qds.ops.Ops import *
from org.autoplot.jythonsupport.JythonOps import *
from org.autoplot.jythonsupport.Util import *
from org.das2.qds import QDataSet
from org.das2.qds.util.BinAverage import *
from org.das2.qds.util import DataSetBuilder

_autoplot_jython_version= 2.00
#_autoplot_jython_version= float(getAutoplotScriptingVersion()[1:])

from org.das2.datum import DatumRange, Units, DatumRangeUtil, TimeUtil
from java.net import URL, URI
from org.das2.datum import TimeParser

# security concerns
#from java.io import File
#from org.das2.util.filesystem import FileSystem
#from org.das2.fsm import FileStorageModel
from org.autoplot.datasource.DataSetURI import getFile
from org.autoplot.datasource.DataSetURI import downloadResourceAsTempFile
#import java
#import org
# end, security concerns.

# jython is tricky with single-jar releases, and using star imports to find classes doesn't work.
#import org.das2
#import org.das2.dataset
#import org.das2.dataset.NoDataInIntervalException
#import org.das2.graph

params= dict()
_paramMap= dict()
_paramSort= []

_scriptTitle= ""
_scriptDescription= ""
_scriptLabel= ""
_scriptIcon= ""

import operator.isNumberType

# name is the name of the input parameter.
# deflt is the default value for the input parameter.
# doc is any documentation for the parameter.
# constraint is used to declare any constraints, presently one of: a list of enumerated values, or a dictionary with VALID_MIN, VALID_MAX and other relevant QDataSet properties.
def getParam( name, deflt, doc='', constraint='' ):
  """<html>get the parameter from the URI<ul>
<li>name is the name of the input parameter.
<li>deflt is the default value for the input parameter.
<li>doc is any documentation for the parameter.
<li>constraint is used to declare any constraints, presently one of: a list of enumerated values, or a dictionary with VALID_MIN, VALID_MAX and other relevant QDataSet properties.<ul>
"""
  if ( type(name).__name__=='int' ):
     name= 'arg_%d' % name
  _paramMap[ name ]= [ name, deflt, doc, constraint ]
  _paramSort.append( name )
  if type(params) is dict:
     if params.has_key(name): 
         t= type(deflt)  # Ed demonstrated this allows some pretty crazy things, e.g. open file, so be careful...
         return t(params[name])
     else:
         return deflt
  else:
     print 'in jython script, variable params was overriden.'
     return deflt

def setScriptTitle(title):
   """Set the title for the script."""
   global _scriptTitle
   _scriptTitle= title

def setScriptDescription(desc):
   """Set a short description for the script.  This can be multiple lines, and maybe html."""
   global _scriptDescription
   _scriptDescription= desc

def setScriptLabel(label):
   """Set a concise identifying label for the script.  This should be no more than a few words."""
   global _scriptLabel
   _scriptLabel= label

def setScriptIcon(icon):
   """Set to a URL which should be a small image file used for an image, representing an action for the script."""
   global _scriptIcon
   _scriptIcon= icon

outputParams= dict()
_outputParamMap= dict()
_outputParamSort= []

# name is the name of the output parameter.
# value is the value of the output parameter.
# doc is any documentation for the output parameter.
# constraint is used to declare any constraints, presently one of: a list of enumerated values, or a dictionary with VALID_MIN, VALID_MAX and other relevant QDataSet properties.
def setOutputParam( name, value, doc='', constraint='' ):
  global _outputParamMap, _outputParamSort, outputParams
  _outputParamMap[ name ]= [ name, value, doc, constraint ]
  _outputParamSort.append( name )
  globals()[name]= value #TODO: this isn't working
  if type(outputParams) is dict:
     outputParams[name]= value
  else:
     raise Exception( 'in jython script, variable outputParams was overriden.' )

# invokeLater command is a scientist-friendly way to define a function that 
# is called on a different thread.
import java.lang.Thread as _Thread
import java.lang.Runnable as _Runnable

class InvokeLaterRunnable( _Runnable ):
   def __init__( self, fun, args, kw ):
      self.fun= fun
      self.args= args
      self.kw= kw
   def run( self ):
      self.fun( *self.args, **self.kw )

def invokeLater( functn, *args, **kw ):
   """invoke the function later.  It should be followed by the parameters 
   passed to the function.  See also runInParallel"""
   r= InvokeLaterRunnable( functn, args, kw )
   # Ed suggests this use ThreadPoolExecutor
   _Thread(r,'invokeLater').start()
