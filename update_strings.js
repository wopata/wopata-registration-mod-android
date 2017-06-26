var Localize = require("localize-with-spreadsheet");
var transformer = Localize.fromGoogleSpreadsheet("1vQHZBdvso7BhR7VK4iz9-lvLuLWEbO52xpbw2XlgtnA", '*');
transformer.setKeyCol('key');

transformer.save("ui/src/main/res/values/strings.xml", { valueCol: "fr", format: "android" });
