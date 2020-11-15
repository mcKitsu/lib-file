package net.mckitsu.lib.file.resource.yaml;

import lombok.Data;
import net.mckitsu.lib.file.resource.yaml.setting.Cipher;
import net.mckitsu.lib.file.resource.yaml.setting.Global;
import net.mckitsu.lib.file.resource.yaml.setting.Party;

import java.util.List;

public @Data class Setting {
    private List<Global> global;
    private List<Party> party;
    private Cipher cipher;
}
