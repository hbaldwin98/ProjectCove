# ProjectCove
Text-based adventure game written in Java using the Swing GUI library. Very limited in scope, only two unique areas, with unending combat at the end. Clues are given in the text of items that can be picked up, or inspected, and areas that can be explored.

## Features
`Go [area]` - `go` allows you to attempt to go to a new area, or inspect something in the area.
`Look [thing]` - Inspect relevant items, areas, or clues in your immediate area.
`Pickup [item]` - `pickup` attempts to pickup an item

### Issues
- If a command is given that is not programmed, no text is output to the window.
- The window will not always update automatically. **Temporary Fix:** Press enter in the textbox to advance text.
