# Lab 4 documentation

## FA.in file strucure
*Note that <> is replaced with {} because md deletes stuff inside <>*
<br>
The FA.in file is organised in headers, followed by lines of information.
Valid headers are:
* ALPHABET
  * Describes the alphabet of the automaton
  <br>
  ALPHABET = ALPHABET \n {values}
  <br>
  values = digits | letters | other_characters
  <br> 
  nonzerodigits means all digits except 0
  <br>
  letters means all letters a-z, A-Z
  
* STATES
    * Describes the states of the automaton
    <br>
    STATES = STATES \n {list_of_states}
    <br>
    list_of_states = {string} | {string} list_of_states 
    
* TRANSITIONS
    * Describes the transitions of the automaton
    * *Note that nonzerodigits means all digits except 0*
  <br>
  TRANSITIONS = TRANSITIONS \n {transitions}
  <br>
  transitions = {transition} | {transition} \n {transitions}
  <br>
  transition = string string string

* INITIAL
    * Chooses the initial state
    <br>
    INITIAL = INITIAL \n string
* END
    * Chooses the ending state(s)
    END = END \n {states}
    <br>
    states = string | string \n {states}

