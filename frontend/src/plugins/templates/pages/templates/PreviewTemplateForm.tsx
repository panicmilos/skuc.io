import { FC } from "react";
import { TextAreaInput } from "../../imports";
import { Template } from "../../models";

type Props = {
  template?: Template
}

export const PreviewTemplateForm: FC<Props> = ({ template }) => {
  
  return (
    <TextAreaInput
      value={template?.template ?? ''}
      onChange={_ => {}}
      disabled={true}
      rows={50}
    />
  );
}