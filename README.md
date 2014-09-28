# Plant UML 9000

My personal version of PlantUML (LGPL version) with fixes and enhancements I need.

## How to use

  1. build PlantUML9000 with ant, the default target will create the file ``plantuml9000.jar``.
  1. after this you can create the pdf file of the Sphinx demo. Go to the ``sphinx-demo`` directory and type ``make latexpdf``.
  1. the resulting pdf can be found in ``sphinx-demo/build/latex``.

## Additions

### Salt9000 integration

This version of PlantUML comes integrated with [Salt9000](https://github/mar9000/salt9000) an ANTLR parser
for the Salt language that comes with PlantUML. Salt9000 however use pure Swing to render UI so the result is as realistic
as the real application.

However this does not work in a *headless* java environment due to the Java rendering constraints.

There is a sample Sphinx project into ``sphinx-demo`` to see how to use PlantUML9000 with Sphinx, that was my final need.

This standard Salt example:

![Salt example](https://raw.githubusercontent.com/mar9000/plantuml/master/test-files/site-salt-example.png)

is renderer this way by Salt9000:

![Salt9000 example](https://raw.githubusercontent.com/mar9000/plantuml/master/test-files/site-salt9000-example.png)

Check the sphinx source to see the source code of these examples.

