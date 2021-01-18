# TYPE_HIERARCHY_MULTIPLE_INTERFACES_RENDERER
Small tool for displaying multiple inheritance with interfaces in Java in ASCII art in console

Kleines Werkzeug zum Anzeigen von Mehrfachvererbung mit Interfaces in Java
==========================================================================

Leider wird in der Java Hierarchy View in Eclipse (und wahrscheinlich auch in IntelliJ, weiss ich nicht genau, ich benutze Eclipse) keine Mehrfachvererbung mit Interfaces angezeigt.

Unfortunately, in the Java Hierarchy View in Eclipse (and probably also in IntelliJ, I'm not sure, I use Eclipse) no multiple inheritance with interfaces is displayed.

Dieses kleine Werkzeug erzeugt eine Ansicht von Mehrfachvererbung mit Interfaces in der Konsole in ASCII-Art.

This little tool creates a view of multiple inheritance with interfaces in the console in ASCII art.

Das Java-Programm sollte ab Java-Version 5 funktionieren. Ich habe Java-Version 13 benutzt, aber keine Spracheigenschaften über Java 5 verwendet.

The Java program should work from Java version 5. I used Java version 13, but didn't use any language properties over Java 5.

Die einzelnen Klassen, Interfaces und Enumerationen werden hierarchisch als Baum angezeigt, wobei senkrechte Linien mit dem Pipe-Zeichen und waagerechte Linien mit dem Bindestrich dargestellt werden.

The individual classes, interfaces and enumerations are displayed hierarchically as a tree, with vertical lines being represented with the pipe symbol and horizontal lines with the hyphen.

Verbindungen zwischen senkrechten und waagerechten Linien werden mit dem Plus-Zeichen dargestellt.

Connections between vertical and horizontal lines are shown with the plus sign.

Verbindungen zwischen senkrechten und waagerechten Linien treten auf, wenn die angezeigte Klasse mindestens ein Interface implementiert oder das angezeigte Interface mehr als nur ein Interface erweitert.

Connections between vertical and horizontal lines occur when the displayed class implements at least one interface or extends the displayed interface more than just one interface.

Unerwünschte Klassen, Interfaces und Enums können ausgeschlossen werden.

Unwanted classes, interfaces and enums can be excluded.

Es gibt auch eine Option zum Erzeugen als Javadoc, wobei das Ergebnis des Durchlaufes aus der Konsole in einen Javadoc-Kommentar kopiert werden kann.

There is also an option to generate as Javadoc, whereby the result of the run can be copied from the console into a Javadoc comment.

In der Eclipse IDE (JDT) kann man in der Javadoc Ansicht das Ergebnis ohne explizites Generieren des Javadoc als HTML ansehen.

In the Eclipse IDE (JDT) you can see the result in the Javadoc view without explicitly generating the Javadoc as HTML.

In der Javadoc-Betriebsart werden die Klassen in Javadoc-Verlinkung ausgegeben.

In the Javadoc operating mode, the classes are output in Javadoc linking annotations.

In der Javadoc-Betriebsart wird jede Zeile mit einem linken Sternchen ausgegeben.

In Javadoc mode, each line is output with a left asterisk.

In der Javadoc-Betriebsart wird der Baum in HTML-Tags pre (preformatted) eingeschlossen, damit die ASCII-Art korrekt angezeigt wird.

In Javadoc mode, the tree is enclosed in pre (preformatted) HTML tags so that the ASCII art is displayed correctly.

In der Javadoc-Betriebsart kann optional auf den Linien ein Tooltip mit dem HTML-Attribut title zur leichteren Navigation im Baum erzeugt werden.

In the Javadoc operating mode, a tooltip with the HTML attribute title can optionally be generated on the lines for easier navigation in the tree.

Dieses Repository ist ein Eclipse-Projekt und kann in Eclipse importiert werden.

This repository is an Eclipse project and can be imported into Eclipse.


Verwendung
----------

Use
---

Kopieren der Klassen dieses Werkzeuges in einen Quell-Ordner im aktuellen Projekt, zum Beispiel mit dem Namen tools.

Copy the classes of this tool into a source folder in the current project, for example with the name tools.

Anpassen des Aufrufes in der main-Methode.

Adapting the call in the main method.

Befüllen Array mit den auszugebenden Klassen, Interfaces und Enumerationen.

Fill the array with the classes, interfaces and enumerations to be output.

Hinzufügen auszuschliessender Klassen, Interfaces und Enumerationen.

Adding excluded classes, interfaces, and enumerations.

Setzen Javadoc-Betriebsart, ja oder nein.

Set Javadoc mode of operation, yes or no.

Start der render-Methode.

Start the render method.

Kopieren der Ausgabe in eigenen Quellcode bei Javadoc-Betriebsart.

Copying the output into your own source code in Javadoc mode.

Eventuell Anlegen eigener Klassen mit main-Methoden für alternative Aufrufe.

Possibly create your own classes with main methods for alternative calls.

Eventuelle zukünftige Erweiterungen
-----------------------------------

Possible future extensions
--------------------------

Optionales Unterdrücken von Package-Namen.

Optional suppression of package names.

Ausschliessen von einfachen und/oder hierarchischen Packages.

Exclusion of simple and / or hierarchical packages.

Finden von Untertypen mit der Reflections Bibliothek (TODO Link).

Finding sub-types with the Reflections library (TODO Link).

Ausgabe im Plant UML Format (TODO Link).

Output in Plant UML format (TODO Link).
